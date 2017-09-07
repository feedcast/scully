package io.feedcast.scully.api

import io.feedcast.scully.models.Episode
import io.finch._
import io.feedcast.scully.services.SearchService

class SearchApi(searchService: SearchService) {
  val search: Endpoint[List[Episode]] = get(/ :: param("query")) { query: String =>
    Ok(searchService.searchFor(query))
  }

  val endpoints = (search)
}
