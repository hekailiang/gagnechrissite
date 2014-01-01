package com.gagnechris.backend

import akka.actor.Actor
import akka.event.Logging
import spray.routing._

object BlogManagementActor {
  case object ListBlogs
  case class RetrieveBlog(blogId: Int)
  case class CreateBlog(blogText: String)
}

class BlogManagementActor extends Actor {
  import BlogManagementActor._

  def receive = {
    case ListBlogs => {

    }
    case blogId: RetrieveBlog => {

    }
    case blogText: CreateBlog => {

    }
  }
}
