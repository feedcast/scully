package io.feedcast.scully

import com.twitter.finagle.Http
import com.twitter.util.Await

import util.Properties
import io.feedcast.scully.api.SearchApi
import io.finch.Text

object Server extends App {
  val port = Properties.envOrElse("PORT", "2024").toInt

  val api = (new SearchApi).endpoints.toServiceAs[Text.Plain]

  Await.ready(Http.server.serve(s":$port", api))
}
