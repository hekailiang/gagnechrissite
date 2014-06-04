package com.gagnechris.backend.client

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Timeouts._
import spray.http.StatusCodes._


class BlogSpec extends FreeSpec with Matchers with ScalaFutures {
  "The Blog " - {
    "when calling Blog.blogPosts" - {
      val blogResponse = Blog.blogPosts

      whenReady(blogResponse, timeout(Span(6, Seconds))) { res =>
        val posts = res.posts.get

        "should return blogs" in {
          posts.length should be > 0
        }
        "first blog should have content" in {
          posts(0).content.isEmpty should be(false)
        }
        "first blog should have author" in {
          posts(0).author.isEmpty should be(false)
        }
      }
    }
  }
}
