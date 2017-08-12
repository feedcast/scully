package io.feedcast.scully.services

import io.feedcast.scully.models.Document

trait IndexService {
  def index(document: Document)
}
