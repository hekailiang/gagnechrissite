package com.gagnechris.backend.client

import com.gagnechris.backend.model.LinkedInToken

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


case class LinkedInAccessToken() {
  import ExecutionContext.Implicits.global
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  import com.gagnechris.backend.BackendConfig.LinkedInConfig

  val apiKey = LinkedInConfig.apiKey
  val apiSecret = LinkedInConfig.apiSecret
  val apiRedirectUri = LinkedInConfig.apiRedirectUri
  var apiAccessToken: Option[LinkedInToken] = None
  val apiAuthState = UUID.randomUUID().toString()
  val authRequestUri = s"https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=$apiKey&state=$apiAuthState&redirect_uri=$apiRedirectUri"

  def generateAccessToken(authCode: String, state: String): Future[Option[LinkedInToken]] = {
    import com.gagnechris.backend.model.LinkedInJsonProtocol._
    import SprayJsonSupport._

    if (state == apiAuthState) {
      val pipeline: HttpRequest => Future[Option[LinkedInToken]] = (
        encode(Gzip)
        ~> sendReceive
        ~> decode(Deflate)
        ~> unmarshal[Option[LinkedInToken]]
      )
      pipeline {
        Post(s"https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code&code=$authCode&redirect_uri=$apiRedirectUri&client_id=$apiKey&client_secret=$apiSecret")
      }
    }
    else Future.failed(new Exception("Auth States did not match.  Possibly due to CSRF."))
  }

  def getAccessToken: Future[Option[LinkedInToken]] = {
    apiAccessToken match {
      case Some(token) => Future.successful(apiAccessToken)
      case None        => Future.successful(None)
    }
  }
}
