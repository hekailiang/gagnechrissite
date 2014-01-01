package com.gagnechris.backend.client

import com.gagnechris.backend.model.GithubToken

import java.util.UUID;

import akka.actor.ActorSystem

import scala.concurrent.{Future, future, ExecutionContext, Await}
import scala.concurrent.duration.DurationInt
import scala.util.{Try, Success, Failure}

import spray.http._
import spray.json.DefaultJsonProtocol
import spray.httpx.encoding.{Gzip, Deflate}
import spray.httpx.SprayJsonSupport
import spray.client.pipelining._
import spray.http.HttpHeaders.Accept
import spray.http.MediaTypes.`application/json`


case class GithubAccessToken() {
  import ExecutionContext.Implicits.global
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  import com.gagnechris.backend.BackendConfig.GithubConfig

  val clientId = GithubConfig.clientId
  val clientSecret = GithubConfig.clientSecret
  val apiRedirectUri = GithubConfig.apiRedirectUri
  var apiAccessToken: Option[GithubToken] = None
  val apiAuthState = UUID.randomUUID().toString()
  val authRequestUri = s"https://github.com/login/oauth/authorize?client_id=$clientId&state=$apiAuthState&redirect_uri=$apiRedirectUri"

  def generateAccessToken(authCode: String, state: String): Future[Option[GithubToken]] = {
    import com.gagnechris.backend.model.GithubJsonProtocol._
    import SprayJsonSupport._

    if (state == apiAuthState) {
      val pipeline: HttpRequest => Future[Option[GithubToken]] = (
        encode(Gzip)
        ~> addHeader(Accept(`application/json`))
        ~> sendReceive
        ~> decode(Deflate)
        ~> unmarshal[Option[GithubToken]]
      )
      pipeline {
        Post(s"https://github.com/login/oauth/access_token?code=$authCode&redirect_uri=$apiRedirectUri&client_id=$clientId&client_secret=$clientSecret")
      }
    }
    else Future.failed(new Exception("Auth States did not match.  Possibly due to CSRF."))
  }

  def getAccessToken: Future[Option[GithubToken]] = {
    apiAccessToken match {
      case Some(token) => Future.successful(apiAccessToken)
      case None        => Future.successful(None)
    }
  }
}
