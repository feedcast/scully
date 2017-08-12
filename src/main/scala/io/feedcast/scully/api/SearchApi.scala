package io.feedcast.scully.api

import io.finch._
import io.feedcast.scully.models.Document
import io.feedcast.scully.services.SearchService

class SearchApi(searchService: SearchService) {
  val search: Endpoint[Seq[Document]] = get(/ :: param("query")) { query: String =>
    Ok(searchService.searchFor(query))
  }

  val endpoints = (search)
}
