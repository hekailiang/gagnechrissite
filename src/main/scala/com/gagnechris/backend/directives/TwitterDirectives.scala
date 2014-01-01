package com.gagnechris.backend.directives

import shapeless.{HNil, ::}
import spray.routing.{Directive, Directives}
import com.gagnechris.backend.client.TwitterBearerToken
import Directives.provide


trait TwitterBearerTokenDirective {
  def twitterBearerToken: Directive[String :: HNil] = {
    provide(TwitterBearerToken.getBearerToken)
  }
}

object TwitterBearerTokenDirective extends TwitterBearerTokenDirective
