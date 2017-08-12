package io.feedcast.scully.services

import io.feedcast.scully.models.Document

trait SearchService {
  def searchFor(query: String) : Seq[Document]
}
