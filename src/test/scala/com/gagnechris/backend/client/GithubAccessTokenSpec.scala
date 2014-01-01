package com.gagnechris.backend.client

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Timeouts._
import spray.http.StatusCodes._


class GithubAccessTokenSpec extends FreeSpec with Matchers with ScalaFutures {
  val githubAccessToken = new GithubAccessToken

  "The Github Access Token" - {
    "when calling githubAccessToken.getAccessToken for the first time" - {
      val accessToken = githubAccessToken.getAccessToken

      whenReady(accessToken, timeout(Span(6, Seconds))) { tokenValue =>
        "should return None" in {
          tokenValue.isEmpty shouldBe (true)
        }
      }
    }
  }
}
