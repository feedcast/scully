package io.feedcast.scully

import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._

object Server extends App {
  lazy val root: Endpoint[String] = get(*) { Ok("Hello") }

  Await.ready(Http.server.serve(":5000", root.toServiceAs[Text.Plain]))
}
