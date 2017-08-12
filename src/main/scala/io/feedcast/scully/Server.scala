package io.feedcast.scully

import java.net.URI

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient
import io.feedcast.scully.services.ElasticSearchService

import util.Properties
import com.twitter.finagle.Http
import com.twitter.finagle.http.Status
import com.twitter.util.Await

import util.Properties
import io.feedcast.scully.api.IndexApi
import io.finch.Text
import io.finch.circe._
import io.circe.generic.auto._
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}

object Server extends App {
  val port = Properties.envOrElse("PORT", "2024").toInt
  val elasticSearchURI = Properties.envOrElse("ELASTIC_SEARCH_URL", "elasticsearch://elastic:changeme@localhost:9200/").toString
  val elasticSearchService = new ElasticSearchService(new URI(elasticSearchURI))
  val indexApi = new IndexApi(elasticSearchService)
  val service = indexApi.endpoints.toServiceAs

  Await.ready(Http.server.serve(s":$port", service))
}
