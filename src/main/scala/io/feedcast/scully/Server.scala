package io.feedcast.scully

import java.net.URI
import util.Properties

import io.feedcast.scully.services.ElasticSearchService

import com.twitter.finagle.Http
import com.twitter.util.Await

import io.feedcast.scully.api.{IndexApi, SearchApi}
import io.finch.circe._
import io.circe.generic.auto._

object Server extends App {
  val port = Properties.envOrElse("PORT", "2024").toInt
  val elasticSearchURI = Properties.envOrElse("ELASTIC_SEARCH_URL", "elasticsearch://elastic:changeme@localhost:9200/").toString

  val elasticSearchService = new ElasticSearchService(new URI(elasticSearchURI))

  val indexApi = new IndexApi(elasticSearchService)
  val searchApi = new SearchApi(elasticSearchService)

  val service = (indexApi.endpoints :+: searchApi.endpoints).toService

  Await.ready(Http.server.serve(s":$port", service))
}
