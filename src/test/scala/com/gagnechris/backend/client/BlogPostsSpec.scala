package com.gagnechris.backend.client

import scala.concurrent._
import scala.concurrent.duration._

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Timeouts._
import spray.http.StatusCodes._


class BlogPostsSpec extends FreeSpec with Matchers with ScalaFutures {
  "The Blog Posts" - {
    "when calling BlogPosts.blogPosts" - {
      val blogResponse = BlogPosts.blogPosts()

      whenReady(blogResponse, timeout(Span(6, Seconds))) { blogPosts =>
        "should return blogs" in {
          blogPosts.size should be > 0
        }
        "first blog should have content" in {
          blogPosts(0).content.isEmpty should be(false)
        }
        "first blog should have author" in {
          blogPosts(0).author.isEmpty should be(false)
        }
      }
    }
  }
}
