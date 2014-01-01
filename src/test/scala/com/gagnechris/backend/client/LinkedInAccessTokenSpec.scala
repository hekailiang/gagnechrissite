package com.gagnechris.backend.client

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Timeouts._
import spray.http.StatusCodes._


class LinkedInAccessTokenSpec extends FreeSpec with Matchers with ScalaFutures {
  val linkedInAccessToken = new LinkedInAccessToken

  "The LinkedIn Access Token" - {
    "when calling LinkedIn.getAccessToken for the first time" - {
      val accessToken = linkedInAccessToken.getAccessToken

      whenReady(accessToken, timeout(Span(6, Seconds))) { tokenValue =>
        "should return None" in {
          tokenValue.isEmpty shouldBe (true)
        }
      }
    }
  }
}
