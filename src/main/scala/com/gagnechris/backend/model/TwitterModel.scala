package com.gagnechris.backend.model

import spray.json.{JsonFormat, DefaultJsonProtocol}
import spray.httpx.SprayJsonSupport


case class TwitterToken(token_type: String, access_token: String)
case class TwitterUser(id: Option[Long], name: Option[String], screen_name: Option[String], profile_image_url: Option[String],
  description: Option[String], lang: Option[String], location: Option[String], favourites_count: Option[Int], followers_count: Option[Int],
  statuses_count: Option[Int], friends_count: Option[Int])
case class Tweet(id: Option[Long], user: Option[TwitterUser], text: Option[String], created_at: Option[String])

object TwitterJsonProtocol extends DefaultJsonProtocol {
  implicit val TwitterTokenFormat = jsonFormat2(TwitterToken)
  implicit val TwitterUserFormat = jsonFormat11(TwitterUser)
  implicit val TweetFormat = jsonFormat4(Tweet)
}


// +      val jsonUnmarshaller: Unmarshaller[Person] = jsonFormat1(Person)
//  +      val xmlUnmarshaller: Unmarshaller[Person] = Unmarshaller.delegate[NodeSeq, Person](`text/xml`) { seq ⇒
//  +        Person(seq.text)
//  +      }
//  +
//  +      implicit val unmarshaller = Unmarshaller.oneOf[Person](jsonUnmarshaller, xmlUnmarshaller)
