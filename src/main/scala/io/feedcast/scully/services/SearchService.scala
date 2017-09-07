package io.feedcast.scully.services

import io.feedcast.scully.models.Episode

trait SearchService {
  def searchFor(query: String) : List[Episode]
}
