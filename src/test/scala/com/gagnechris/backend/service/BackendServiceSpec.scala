package com.gagnechris.backend.service

import org.scalatest.BeforeAndAfterAll
import org.scalatest.FreeSpec
import org.scalatest.Matchers

import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest


class BackendServiceSpec extends FreeSpec with ScalatestRouteTest with BackendService with Matchers {
  def actorRefFactory = system

  override def afterAll(): Unit = system.shutdown

  "The Backend Service" - {
    "when calling GET api/TwitterTweets" - {
      "should return OK" in {
        Get("/api/TwitterTweets") ~> apiRoute ~> check {
          status should equal(OK)
        }
      }
    }
    "when calling GET api/LinkedInProfile for first time" - {
      "should return TemporaryRedirect" in {
        Get("/api/LinkedInProfile") ~> apiRoute ~> check {
          status should equal(TemporaryRedirect)
        }
      }
    }
  }
}
