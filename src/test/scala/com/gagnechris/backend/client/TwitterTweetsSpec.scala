package com.gagnechris.backend.client

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Timeouts._
import spray.http.StatusCodes._


class TwitterTweetsSpec extends FreeSpec with Matchers with ScalaFutures {
  "The Twitter Tweets" - {
    "when calling TwitterTweets.tweets" - {
      val twitterResponse = TwitterTweets.tweets(TwitterBearerToken.getBearerToken)

      whenReady(twitterResponse, timeout(Span(6, Seconds))) { tweets =>
        "should return tweets" in {
          tweets.size should be > 0
        }
        "first tweet should have text" in {
          tweets(0).text.isEmpty should be(false)
        }
        "first tweet should have user" in {
          tweets(0).user.isEmpty should be(false)
        }
      }
    }
    "when calling TwitterTweets.tweets with an invalid token" - {
      "should return error response" in {
        pending
        val twitterResponse = TwitterTweets.tweets("FakeToken")

        whenReady(twitterResponse, timeout(Span(6, Seconds))) { response =>
          "should return valid error" in {
            pending
          }
        }
      }
    }
  }
}
