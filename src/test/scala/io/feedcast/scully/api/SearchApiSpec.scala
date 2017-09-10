package io.feedcast.api

import org.scalatest.{FunSpec, Matchers}
import com.twitter.finagle.http.Status
import io.finch.Input
import io.feedcast.scully.api.SearchApi
import io.feedcast.scully.models.Episode
import io.feedcast.scully.services.SearchService

class DummySearchService extends SearchService {
  def searchFor(query: String): List[Episode] = {
    List(
      Episode("a23", "/foo/bar", "Jungle", Option(""), Option("")),
      Episode("a24", "/foo/bar2", "Jungle 2", Option(""), Option(""))
    )
  }
}

class SearchApiSpec extends FunSpec with Matchers {
  val search = new SearchApi(new DummySearchService).endpoints
  val validSearch = Input.get("/?query=term")

  describe("GET /?query=term") {
    it("returns with status ok") {
      search(validSearch).awaitOutputUnsafe().map(_.status) shouldBe Some(Status.Ok)
    }

    it("returns a list of episodes") {
      search(validSearch).awaitValue().map(_.get) shouldBe
        Some(
          List(
            Episode("a23", "/foo/bar", "Jungle", Option(""), Option("")),
            Episode("a24", "/foo/bar2", "Jungle 2", Option(""), Option(""))
          )
        )
    }
  }
}
