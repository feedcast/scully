package io.feedcast.scully.services

import java.net.{URI, URL}

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient
import io.feedcast.scully.models.{Episode, EpisodeHitHeader}
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}

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

  def index(episode : Episode) = {
    client.execute {
      indexInto(episode.index / episode.`type`).doc(episode.toJson) id episode.id
    }.await
  }

  def searchFor(query: String): List[Episode] = {
    val response = client.execute {
      search("documents" / "episode") query(query) limit(100)
    }.await

    response.to[Episode](EpisodeHitHeader).toList
  }
}
