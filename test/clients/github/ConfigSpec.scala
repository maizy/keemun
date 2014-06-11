package test

import org.specs2.mutable._
import org.specs2.runner._

import org.junit.runner._

import hedgehog.clients.github.Config

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
@RunWith(classOf[JUnitRunner])
class ConfigSpec extends Specification {
  "Config" should {

    "has apiBaseUrl property" in {
      val config = new Config(apiBaseUrl = "http://base")
      config.apiBaseUrl must equalTo("http://base")
    }
  }

  "Config.replaceBaseUrl" should {

    "work" in {
      val config = new Config(apiBaseUrl = "http://base")
      config.replaceBaseUrl("https://api.github.com/some/resource") must equalTo("http://base/some/resource")
    }

    "replace base url with base path" in {
      val config = new Config("http://base/path")
      config.replaceBaseUrl("https://api.github.com/some/resource") must equalTo("http://base/path/some/resource")
    }

    "replace base url with port" in {
      val config = new Config("http://base:8880")
      config.replaceBaseUrl("https://api.github.com/some/resource") must equalTo("http://base:8880/some/resource")
    }

    "replace url without path" in {
      val config = new Config("http://base:8880")
      config.replaceBaseUrl("https://api.github.com/") must equalTo("http://base:8880/")
      config.replaceBaseUrl("https://api.github.com") must equalTo("http://base:8880")
    }

    "replace url with protocol" in {
      val config = new Config("http://base:8880")
      config.replaceBaseUrl("http://api.github.com/path") must equalTo("http://base:8880/path")
      config.replaceBaseUrl("https://api.github.com/path") must equalTo("http://base:8880/path")
    }

    "replace url without protocol" in {
      val config = new Config("http://base:8880")
      config.replaceBaseUrl("api.github.com/") must equalTo("http://base:8880/")
      config.replaceBaseUrl("api.github.com") must equalTo("http://base:8880")
      config.replaceBaseUrl("api.github.com/status") must equalTo("http://base:8880/status")
    }
  }
}
