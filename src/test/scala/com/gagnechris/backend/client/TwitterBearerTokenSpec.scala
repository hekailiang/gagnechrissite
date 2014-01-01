package com.gagnechris.backend.client

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Timeouts._
import spray.http.StatusCodes._


class TwitterBearerTokenSpec extends FreeSpec with Matchers with ScalaFutures {
  "The Twitter Bearer Token" - {
    "when calling TwitterBearerToken.getBearerToken" - {
      val token: String = TwitterBearerToken.getBearerToken
      "should return token" in {
        token.isEmpty should be(false)
      }
    }
  }
}
