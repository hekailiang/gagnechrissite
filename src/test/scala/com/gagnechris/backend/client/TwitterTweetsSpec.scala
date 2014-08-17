package com.gagnechris.backend.client

import com.gagnechris.backend.model._

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{ FreeSpec, Matchers }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Seconds, Span }
import org.scalatest.concurrent.Timeouts._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import spray.http._

class TwitterTweetsSpec extends FreeSpec with Matchers with MockitoSugar {
  val mockResponse = mock[HttpResponse]
  val mockStatus = mock[StatusCode]
  when(mockResponse.status) thenReturn mockStatus
  when(mockStatus.isSuccess) thenReturn true
  //mockFailureStatus.isSuccess returns false

  val jsonValidPayload = """
    [
      {
        "id": 1234,
        "user": {
          "id": 4567,
          "name": "Chris Gagne",
          "screen_name": "gagnechris2",
          "profile_image_url": "http://photo.jpeg",
          "description": "hands-on dev manager",
          "lang": "en",
          "location": "New York City",
          "favourites_count": 20,
          "followers_count": 29,
          "statuses_count": 53,
          "friends_count": 113
        },
        "text": "tweet text",
        "created_at": "Sun Aug 17 01:05:38 +0000 2014"
      }
    ]
    """

  val jsonInvalidPayload = """
    {
      "badfield" : "badfieldvalue"
    }
    """

  val body = HttpEntity(ContentTypes.`application/json`, jsonValidPayload.getBytes())
  when(mockResponse.entity) thenReturn body

  object TwitterTweets extends TweetsService {
    override def sendAndReceive = {
      (req:HttpRequest) => Promise.successful(mockResponse).future
    }
  }

  "The Twitter Tweets" - {
    "when calling TwitterTweets.tweets" - {
      val twitterResponse = TwitterTweets.tweets("faketoken")
      val tweets = Await.result(twitterResponse, Span(2, Seconds))
      val expectedTweets = List(Tweet(Some(1234),Some(TwitterUser(Some(4567), Some("Chris Gagne"), Some("gagnechris2"), Some("http://photo.jpeg"), Some("hands-on dev manager"), Some("en"), Some("New York City"), Some(20), Some(29), Some(53), Some(113))), Some("tweet text"), Some("Sun Aug 17 01:05:38 +0000 2014")))

      "should return expected tweets" in {
        tweets shouldEqual expectedTweets
      }
    }

    "when calling TwitterTweets.tweets with an invalid token" - {
      "should return error response" in {
        pending
      }
    }

    "when calling TwitterTweets.tweets but unexpected result format is returned" - {
      "should return error response" in {
        pending
      }
    }
  }
}
