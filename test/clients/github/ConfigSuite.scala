package test

import org.scalatest._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
@RunWith(classOf[JUnitRunner])
class ConfigSuite extends FunSuite with Matchers {

  import hedgehog.clients.github.Config

  trait SampleConfigs {
    val config = new Config(apiBaseUrl = "http://base")
    val configWithPort = new Config(apiBaseUrl = "http://base:8880")
  }

  test("apiBaseUrl property") {
    new SampleConfigs {
      assert(config.apiBaseUrl === "http://base")
    }
  }

  test("replace base url with base path") {
    new SampleConfigs {
      assert(config.replaceBaseUrl("https://api.github.com/some/resource") === "http://base/some/resource")
    }
  }
  
  test("replace base url with port") {
    new SampleConfigs {
      assert(configWithPort.replaceBaseUrl("https://api.github.com/some/resource") === "http://base:8880/some/resource")
    }
  }

  test("replace base url without path") {
    new SampleConfigs {
      assert(configWithPort.replaceBaseUrl("https://api.github.com/") === "http://base:8880/")
      assert(configWithPort.replaceBaseUrl("https://api.github.com") === "http://base:8880")
    }
  }

  test("replace base url with protocol") {
    new SampleConfigs {
      assert(configWithPort.replaceBaseUrl("http://api.github.com/path") === "http://base:8880/path")
      assert(configWithPort.replaceBaseUrl("https://api.github.com/path") === "http://base:8880/path")
    }
  }

  test("replace base url without protocol") {
    new SampleConfigs {
      assert(configWithPort.replaceBaseUrl("api.github.com/") === "http://base:8880/")
      assert(configWithPort.replaceBaseUrl("api.github.com") === "http://base:8880")
      assert(configWithPort.replaceBaseUrl("api.github.com/status") === "http://base:8880/status")
    }
  }
}
