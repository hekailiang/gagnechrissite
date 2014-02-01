package com.gagnechris.backend.service

import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration.{Duration, DurationInt}
import scala.util.{Try, Success, Failure}

import akka.actor.Actor

import spray.routing._
import spray.http.HttpResponse
import spray.http.StatusCodes.{OK, TemporaryRedirect, InternalServerError}
import spray.routing.directives.CachingDirectives._
import spray.routing.directives.MarshallingDirectives._
import spray.json._
import DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport
import spray.httpx.unmarshalling.{MalformedContent, Unmarshaller, Deserialized}
import SprayJsonSupport._

import com.gagnechris.backend.client._

import shapeless.HList


class BackendServiceActor extends Actor with BackendService {
  def actorRefFactory = context
  def receive = runRoute(apiRoute)
}

trait BackendService extends HttpService with SprayJsonSupport {
  implicit def executionContext: ExecutionContext = actorRefFactory.dispatcher

  def getPath[L <: HList](pm: PathMatcher[L]) = get & path(pm)
  def postPath[L <: HList](pm: PathMatcher[L]) = post & path(pm)

  val cache30min = routeCache(maxCapacity = 1000, timeToLive = Duration("30 min"))
  val linkedInAccessToken = new LinkedInAccessToken
  val githubAccessToken = new GithubAccessToken

  def apiRoute: Route = pathPrefix("api") {
    blogRoute ~ twitterRoute ~ linkedInRoute ~ githubRoute
  }

  def blogRoute: Route = getPath("BlogPosts" / IntNumber) { blogId =>
    complete {
      s"BlogPost - Get Blog Post ${blogId}"
    }
  }

  def twitterRoute: Route = getPath("TwitterTweets") {
    import com.gagnechris.backend.model.TwitterJsonProtocol.TweetFormat

    cache(cache30min) {
      onComplete(TwitterTweets.tweets(TwitterBearerToken.getBearerToken)) {
        case Success(tweets) => {
          respondWithStatus(OK) {
            complete(tweets.toJson.toString)
          }
        }
        case Failure(ex) => {
          val errorMsg = ex.getMessage
          complete(InternalServerError, s"twitterRoute Error: $errorMsg")
        }
      }
    }
  }

  def linkedInRoute: Route =
    getPath("LinkedInProfile") {
      import com.gagnechris.backend.model.LinkedInJsonProtocol._

      onSuccess(linkedInAccessToken.getAccessToken) { token =>
        token match {
          case Some(tokenValue) => {
            cache(cache30min) {
              onComplete(LinkedInProfile.profile(tokenValue.access_token)) {
                case Success(profile) => {
                  respondWithStatus(OK) {
                    linkedInAccessToken.apiAccessToken = None // Reset access token since they expire
                    complete(profile.toJson.toString)
                  }
                }
                case Failure(ex) => {
                  val errorMsg = ex.getMessage
                  complete(InternalServerError, s"linkedInRoute Error: $errorMsg")
                }
              }
            }
          }
          case None => redirect(linkedInAccessToken.authRequestUri, TemporaryRedirect)
        }
      }
    } ~
    getPath("LinkedInAuthCode") {
      import com.gagnechris.backend.model.LinkedInJsonProtocol._

      parameters('code, 'state) { (code, state) =>
        onComplete(linkedInAccessToken.generateAccessToken(code, state)) {
          case Success(token) => {
            linkedInAccessToken.apiAccessToken = token
            redirect("/api/LinkedInProfile", TemporaryRedirect)
          }
          case Failure(ex) => {
            val errorMsg = ex.getMessage
            complete(InternalServerError, s"linkedInRoute Error: $errorMsg")
          }
        }
      }
    }

  def githubRoute: Route =
    getPath("GithubRepos") {
      import com.gagnechris.backend.model.GithubJsonProtocol._

      onSuccess(githubAccessToken.getAccessToken) { token =>
        token match {
          case Some(tokenValue) => {
            cache(cache30min) {
              complete(GithubRepos.getUserAndRepos(tokenValue.access_token))
            }
          }
          case None => redirect(githubAccessToken.authRequestUri, TemporaryRedirect)
        }
      }
    } ~
    getPath("GithubAuthCode") {
      import com.gagnechris.backend.model.GithubJsonProtocol._

      parameters('code, 'state) { (code, state) =>
        onComplete(githubAccessToken.generateAccessToken(code, state)) {
          case Success(token) => {
            githubAccessToken.apiAccessToken = token
            redirect("/api/GithubRepos", TemporaryRedirect)
          }
          case Failure(ex) => {
            val errorMsg = ex.getMessage
            complete(InternalServerError, s"githubRoute Error: $errorMsg")
          }
        }
      }
    }
}
