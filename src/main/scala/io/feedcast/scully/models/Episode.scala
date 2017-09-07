package io.feedcast.scully.models

import com.sksamuel.elastic4s.{Hit, HitReader}
import io.circe.generic.auto._
import io.circe.syntax._

case class Episode(uuid: String, title: String) {
  val index = "documents"
  val `type` = "episode"

  def id: String = {
    this.uuid
  }

  def _type: String = {
    this.`type`
  }

  def toJson: String = {
    this.asJson.toString
  }
}

object EpisodeHitHeader extends HitReader[Episode] {
  override def read(hit: Hit): Either[Throwable, Episode] = {
    Right(
      Episode(
        hit.sourceAsMap("uuid").toString,
        hit.sourceAsMap("title").toString
      )
    )
  }
}
