package test

import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import play.api.test.Helpers._
import org.junit.runner._


@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    for (url <- List("/", "/search")) {
      s"send redirect for $url" in {
        running(FakeApplication()) {
          val response = route(FakeRequest(GET, url)).get
          redirectLocation(response) must beEqualTo(Some("/search/code"))
        }
      }
    }
  }
}
