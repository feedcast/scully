package io.feedcast.scully.models

import io.circe.generic.auto._
import io.circe.syntax._

case class Episode(uuid: String, title: String) extends Document {
  override val _type = "episode"

  override def id: String = { this.uuid }
  override def toJson: String = { this.asJson.toString }
}
