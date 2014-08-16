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

import com.gagnechris.backend.model.{BlogPosts, BlogPost, BlogAuthor}


object Blog {
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  import com.gagnechris.backend.BackendConfig.BlogConfig

  val wordpressSite = BlogConfig.wordpressSite
  val baseUrl = BlogConfig.url

  def blogPosts: Future[BlogPosts] = {
    import com.gagnechris.backend.model.BlogJsonProtocol._
    import SprayJsonSupport._

    val pipeline: HttpRequest => Future[BlogPosts] = (
      encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[BlogPosts]
    )

    pipeline {
      Get(s"$baseUrl/rest/v1/sites/$wordpressSite/posts/")
    }
  }

  def blogPost(blogId: Int): Future[BlogPosts] = {
    import com.gagnechris.backend.model.BlogJsonProtocol._
    import SprayJsonSupport._

    val pipeline: HttpRequest => Future[BlogPosts] = (
      encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[BlogPosts]
    )

    pipeline {
      Get(s"$baseUrl/rest/v1/sites/$wordpressSite/posts/$blogId")
    }
  }
}
