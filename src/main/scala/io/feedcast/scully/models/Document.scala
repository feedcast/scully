package io.feedcast.scully.models

abstract class Document {
  val _index = "documents"
  val _type = "document"

  def id: String
  def toJson: String
}
