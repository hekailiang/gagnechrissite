package com.gagnechris.backend.directives

import org.scalatest.BeforeAndAfterAll
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import spray.testkit.ScalatestRouteTest
import spray.routing._
import Directives._
import spray.http.StatusCodes._

class TwitterBearerTokenDirectiveSpec extends FreeSpec with ScalatestRouteTest with Matchers {
  import TwitterBearerTokenDirective._

  "The TwitterBearerTokenDirective" - {
    "extracts the bearer token" in {
      Get("/") ~> twitterBearerToken { token => {
          token.length should be > 0
          complete(token)
        }} ~> check {
        status should equal(OK)
      }
    }
  }
}
