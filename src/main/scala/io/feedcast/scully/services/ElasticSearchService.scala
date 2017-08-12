package io.feedcast.scully.services

import java.net.{URI, URL}

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient
import io.feedcast.scully.models.Document
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}

class ElasticSearchService(uri: URI) extends IndexService {
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
      indexInto(document._index / document._type).doc(document.toJson) id document.id
    }.await
  }
}
