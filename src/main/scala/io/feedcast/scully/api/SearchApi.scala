package io.feedcast.scully.api

import io.finch._

class SearchApi {
  val search = get(/) { Ok("Hello") }

  val endpoints = (search)
}
