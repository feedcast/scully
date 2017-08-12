package io.feedcast.scully.api

import com.twitter.finagle.http.Status
import io.feedcast.scully.services.IndexService
import io.finch._
import io.finch.circe._
import io.circe._
import io.circe.generic.auto._
import io.feedcast.scully.models.Document
import io.finch.Error.NotParsed

class IndexApi(indexService: IndexService) {
  val indexEpisode: Endpoint[Unit] = put("index" :: jsonBody[Document]) { document: Document =>
    indexService.index(document)

    Output.empty(Status.Accepted)
  } handle {
    case e: NotParsed => BadRequest(e)
  }

  implicit val encodeException: Encoder[Exception] = Encoder.instance(e =>
    Json.obj("message" -> Json.fromString(e.getMessage)))

  val endpoints = (indexEpisode)
}
