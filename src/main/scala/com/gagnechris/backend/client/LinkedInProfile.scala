package com.gagnechris.backend.client

import com.gagnechris.backend.model.{LinkedInProfile => LinkedInProfileModel}

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


object LinkedInProfile {
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  import com.gagnechris.backend.BackendConfig.LinkedInConfig

  val format = LinkedInConfig.format
  val profileFields = LinkedInConfig.profileFields

  def profile(token: String): Future[LinkedInProfileModel] = {
    import com.gagnechris.backend.model.LinkedInJsonProtocol._
    import SprayJsonSupport._

    val pipeline: HttpRequest => Future[LinkedInProfileModel] = (
      encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[LinkedInProfileModel]
    )
    pipeline {
      Get(s"https://api.linkedin.com/v1/people/~:($profileFields)?oauth2_access_token=$token&format=$format")
    }
  }
}
