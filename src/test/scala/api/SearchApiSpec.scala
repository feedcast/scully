package api

import org.scalatest.{FunSpec, Matchers}
import com.twitter.finagle.http.Status
import com.twitter.util.Return
import io.finch.Input
import io.feedcast.scully.api.SearchApi

class SearchApiSpec extends FunSpec with Matchers {
  val search = (new SearchApi).endpoints

  describe("GET /") {
    it("returns with status ok") {
      search(Input.get("/")).awaitOutputUnsafe().map(_.status) shouldBe Some(Status.Ok)
    }

    it("returns with hello in the body") {
      search(Input.get("/")).awaitValueUnsafe().get shouldBe "Hello"
    }
  }
}
