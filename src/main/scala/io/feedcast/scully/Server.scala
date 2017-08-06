package io.feedcast.scully

import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._
import util.Properties

object Server extends App {
  val port = Properties.envOrElse("PORT", "2024").toInt
  val root = get(*) { Ok("Hello") }

  Await.ready(Http.server.serve(s":$port", root.toServiceAs[Text.Plain]))
}
