package io.feedcast.scully.services

import java.net.URI

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder

import io.feedcast.scully.models.Document
import io.circe._
import io.circe.generic.auto._
import com.sksamuel.elastic4s.circe._

class ElasticSearchService(uri: URI) extends IndexService with SearchService {
  import com.sksamuel.elastic4s.http.ElasticDsl._

  lazy val connectionUri = ElasticsearchClientUri(uri.getHost, uri.getPort)
  lazy val connectionCredentials = new UsernamePasswordCredentials(uri.getRawUserInfo)
  lazy val connectionProvider =  new BasicCredentialsProvider
  lazy val connectionAuthCallback = new HttpClientConfigCallback {
    override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder) = {
      connectionProvider.setCredentials(AuthScope.ANY, connectionCredentials)
      httpClientBuilder.setDefaultCredentialsProvider(connectionProvider)
    }
  }
  lazy val connectionRequestConfigCallback = new RequestConfigCallback {
    override def customizeRequestConfig(requestConfigBuilder: Builder) = {
      requestConfigBuilder
    }
  }
  val client = HttpClient(connectionUri, connectionRequestConfigCallback, connectionAuthCallback )

  def index(document : Document) = {
    client.execute {
      indexInto(document.index / document.`type`).doc(document.toJson) id document.uuid
    }.await
  }

  def searchFor(query: String): Seq[Document] = {
    val response = client.execute {
      search("documents" / "document") query(query) limit(100)
    }.await

    response.to[Document]
  }
}

