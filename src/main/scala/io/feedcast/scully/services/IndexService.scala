package io.feedcast.scully.services

import io.feedcast.scully.models.Episode

trait IndexService {
  def index(episode: Episode)
}
