package com.gagnechris.backend.model

import spray.json.{JsonFormat, DefaultJsonProtocol}


case class BlogPosts(found: Option[Long], posts: Option[List[BlogPost]])
case class BlogPost(id: Option[Long], author: Option[BlogAuthor], date: Option[String], modified: Option[String],
  title: Option[String], content: Option[String], excerpt: Option[String], status: Option[String])
case class BlogAuthor(id: Option[Long], name: Option[String], avatar_URL: Option[String])

object BlogJsonProtocol extends DefaultJsonProtocol {
  implicit val BlogAuthorFormat = jsonFormat3(BlogAuthor)
  implicit val BlogPostFormat = jsonFormat8(BlogPost)
  implicit val BlogPostsFormat = jsonFormat2(BlogPosts)
}
