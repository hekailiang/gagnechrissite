package com.gagnechris.backend.client

import akka.actor.ActorSystem

import scala.concurrent.{Future, future, ExecutionContext, Await}
import scala.concurrent.duration.DurationInt
import scala.util.Try

import spray.http._
import spray.json.DefaultJsonProtocol
import spray.httpx.encoding.{Gzip, Deflate}
import spray.httpx.SprayJsonSupport
import spray.httpx.unmarshalling.{MalformedContent, Unmarshaller, Deserialized}
import spray.client.pipelining._

import com.gagnechris.backend.model.{Tweet, TwitterUser}

object TwitterTweets extends TweetsService

trait TweetsService {
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  import com.gagnechris.backend.BackendConfig.TwitterConfig

  val userName = TwitterConfig.userName
  val baseUrl = TwitterConfig.url

  def sendAndReceive = sendReceive

  def tweets(bearerToken: String):  Future[List[Tweet]] = {

    import com.gagnechris.backend.model.TwitterJsonProtocol._
    import SprayJsonSupport._

    // Improve to deserialize error?  https://groups.google.com/forum/#!topic/spray-user/N6RGjXLGC-Q/discussion
    val pipeline: HttpRequest => Future[List[Tweet]] = (
      addHeader("Authorization", s"Bearer $bearerToken")
      ~> encode(Gzip)
      ~> sendAndReceive
      ~> decode(Deflate)
      ~> unmarshal[List[Tweet]]
    )

    pipeline {
      Get(s"$baseUrl/1.1/statuses/user_timeline.json?screen_name=$userName&count=50&include_rts=true&exclude_replies=true")
    }
  }
}
