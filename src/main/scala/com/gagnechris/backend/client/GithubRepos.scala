package com.gagnechris.backend.client

import com.gagnechris.backend.model.{GithubRepo, GithubUser, GithubData}

import java.util.UUID;

import akka.actor.ActorSystem

import scala.concurrent.{Future, future, ExecutionContext, Await}
import scala.concurrent.duration.DurationInt
import scala.util.{Try, Success, Failure}

import spray.http._
import spray.json.DefaultJsonProtocol
import spray.httpx.encoding.{Gzip, Deflate}
import spray.httpx.SprayJsonSupport
import spray.client.pipelining._


object GithubRepos {
  import ExecutionContext.Implicits.global
  implicit val system = ActorSystem()
  import system.dispatcher // execution context for futures

  import com.gagnechris.backend.BackendConfig.GithubConfig

  val url = GithubConfig.url

  def getUserAndRepos(token: String): GithubData = {
    val userData = Await.result(user(token), 2 seconds)
    val reposData = Await.result(repos(token), 2 seconds)
    new GithubData(userData, reposData)
  }

  def user(token: String): Future[GithubUser] = {
    import com.gagnechris.backend.model.GithubJsonProtocol._
    import SprayJsonSupport._

    val pipeline: HttpRequest => Future[GithubUser] = (
      addHeader("Authorization", s"token $token")
      ~> encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[GithubUser]
    )
    pipeline {
      Get(s"$url/user")
    }
  }

  def repos(token: String): Future[List[GithubRepo]] = {
    import com.gagnechris.backend.model.GithubJsonProtocol._
    import SprayJsonSupport._

    val pipeline: HttpRequest => Future[List[GithubRepo]] = (
      addHeader("Authorization", s"token $token")
      ~> encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[List[GithubRepo]]
    )
    pipeline {
      Get(s"$url/user/repos")
    }
  }
}
