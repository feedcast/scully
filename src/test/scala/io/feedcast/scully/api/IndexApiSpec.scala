package io.feedcast.api

import org.scalatest.{FunSpec, Matchers}
import com.twitter.finagle.http.Status
import io.finch.Input
import io.feedcast.scully.api.IndexApi
import io.feedcast.scully.models.Episode
import io.feedcast.scully.services.IndexService

class DummyIndexService extends IndexService {
  def index(episode: Episode) { }
}

class IndexApiSpec extends FunSpec with Matchers {
  val index = new IndexApi(new DummyIndexService).endpoints
  val validEpisode = Input.put("/index/episode").withBody(Episode("a23", "/foo/bar", "foo").toJson)
  val invalidEpisode = Input.put("/index/episode").withBody("invalid")

  describe("PUT /index/episode") {
    describe("when an valid episode is given") {
      it("returns with status accepted") {
        index(validEpisode).awaitOutputUnsafe().map(_.status) shouldBe Some(Status.Accepted)
      }
    }

    describe("when an invalid episode is given") {
      it("returns with status bad request") {
        index(invalidEpisode).awaitOutputUnsafe().map(_.status) shouldBe Some(Status.BadRequest)
      }
    }
  }
}
