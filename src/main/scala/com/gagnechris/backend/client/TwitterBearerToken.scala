package com.gagnechris.backend.client

import akka.actor.ActorSystem

import org.apache.commons.codec.binary.Base64

import scala.concurrent.{Future, future, ExecutionContext, Await}
import scala.concurrent.duration.DurationInt
import scala.util.{Success, Failure}

import spray.http._
import spray.json.DefaultJsonProtocol
import spray.httpx.encoding.{Gzip, Deflate}
import spray.httpx.SprayJsonSupport
import spray.client.pipelining._

import com.gagnechris.backend.model.TwitterToken


object TwitterBearerToken extends TwitterBearerToken {
  import com.gagnechris.backend.BackendConfig.TwitterConfig

  val twitterBaseUrl = TwitterConfig.url
  val consumerKey = TwitterConfig.consumerKey
  val consumerSecret = TwitterConfig.consumerSecret
  val credentials = Base64.encodeBase64String(s"$consumerKey:$consumerSecret".getBytes())

  def getBearerToken: String = {
    generateBearerToken(credentials, twitterBaseUrl)
  }
}

trait TwitterBearerToken {
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  def generateBearerToken(credentials: String, baseUrl: String): String = {
    import com.gagnechris.backend.model.TwitterJsonProtocol.TwitterTokenFormat
    import SprayJsonSupport._

    val pipeline: HttpRequest => Future[TwitterToken] = (
      addHeader("Authorization", s"Basic $credentials")
      ~> encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[TwitterToken]
    )
    val response = pipeline {
      Post(s"$baseUrl/oauth2/token", FormData(Map("grant_type" -> "client_credentials")))
    }
    Await.result(response, 5 seconds).access_token
  }
}
