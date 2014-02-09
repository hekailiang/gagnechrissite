package com.gagnechris.backend

import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

object BackendConfig {

  val configPrivate: Config = ConfigFactory.load("application_private.conf").getConfig("gagnechris.backend")
  val config: Config = ConfigFactory.load.getConfig("gagnechris.backend").withFallback(configPrivate)

  lazy val MajorVersion = config.getInt("version.major")
  lazy val MinorVersion = config.getInt("version.minor")
  lazy val Environment = System.getProperty("DSA_ENV") match {
    case null => "dev"
    case "" => "dev"
    case x: String => x
  }

  object HttpConfig {
    private val httpConfig = config.getConfig("http")

    lazy val Interface = httpConfig.getString("interface")
    lazy val Port = httpConfig.getInt("port")
  }

  object BlogConfig {
    private val blogConfig = config.getConfig("blog")
    lazy val url = blogConfig.getString("url")
    lazy val blogType = blogConfig.getString("type")
    lazy val wordpressSite = blogConfig.getString("wordpress-site")
  }

  object TwitterConfig {
    private val twitterConfig = config.getConfig("twitter")
    lazy val url = twitterConfig.getString("url")
    lazy val enabled = twitterConfig.getBoolean("enabled")
    lazy val consumerKey = twitterConfig.getString("consumer-key")
    lazy val consumerSecret = twitterConfig.getString("consumer-secret")
    lazy val userKey = twitterConfig.getString("user-key")
    lazy val userSecret = twitterConfig.getString("user-secret")
    lazy val userName = twitterConfig.getString("user-name")
  }

  object LinkedInConfig {
    private val linkedInConfig = config.getConfig("linkedin")
    lazy val enabled = linkedInConfig.getBoolean("enabled")
    lazy val apiKey = linkedInConfig.getString("api-key")
    lazy val apiSecret = linkedInConfig.getString("api-secret")
    lazy val userKey = linkedInConfig.getString("user-key")
    lazy val userSecret = linkedInConfig.getString("user-secret")
    lazy val apiRedirectUri = linkedInConfig.getString("api-redirect-uri")
    lazy val format = linkedInConfig.getString("format")
    lazy val profileFields = linkedInConfig.getString("profile-fields")
  }

  object GithubConfig {
    private val githubConfig = config.getConfig("github")
    lazy val enabled = githubConfig.getBoolean("enabled")
    lazy val url = githubConfig.getString("url")
    lazy val clientId = githubConfig.getString("client-id")
    lazy val clientSecret = githubConfig.getString("client-secret")
    lazy val apiRedirectUri = githubConfig.getString("api-redirect-uri")
  }
}
