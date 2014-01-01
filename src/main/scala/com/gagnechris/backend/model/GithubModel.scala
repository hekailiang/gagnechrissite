package com.gagnechris.backend.model

import spray.json.{JsonFormat, DefaultJsonProtocol}

case class GithubUser(login: Option[String], id: Option[Long], avatar_url: Option[String], `type`: Option[String], name: Option[String], company: Option[String],
  blog: Option[String], location: Option[String], email: Option[String], bio: Option[String], public_repos: Option[Int], public_gists: Option[Int], followers: Option[Int],
  following: Option[Int], created_at: Option[String], updated_at: Option[String])
case class GithubRepo(id: Option[Long], name: Option[String], full_name: Option[String], html_url: Option[String], description: Option[String], fork: Option[Boolean],
  created_at: Option[String], updated_at: Option[String], pushed_at: Option[String], clone_url: Option[String], homepage: Option[String], size: Option[Int], language: Option[String],
  forks: Option[Int], open_issues: Option[Int], watchers: Option[Int], open_issues_count: Option[Int])
case class GithubData(user: GithubUser, repos: List[GithubRepo])
case class GithubToken(access_token: String, scope: String, token_type: String)

object GithubJsonProtocol extends DefaultJsonProtocol {
  implicit def GithubUserFormat = jsonFormat16(GithubUser)
  implicit def GithubRepoFormat = jsonFormat17(GithubRepo)
  implicit def GithubTokenFormat = jsonFormat3(GithubToken)
  implicit def GithubDataFormat = jsonFormat2(GithubData)
}
