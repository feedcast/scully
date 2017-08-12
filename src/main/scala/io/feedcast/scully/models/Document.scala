package io.feedcast.scully.models

import com.sksamuel.elastic4s.{Hit, HitReader}
import io.circe.generic.auto._
import io.circe.syntax._

case class Document(uuid: String, `type`: String, title: String) {
  val index = "documents"

  def toJson: String = { this.asJson.toString }

  implicit object DocumentHitReader extends HitReader[Document] {
    override def read(hit: Hit): Either[Throwable, Document] = {
      Right(
        Document(
          "foo",
          "episode",
          "fooo"
        )
      )
    }
  }
}

