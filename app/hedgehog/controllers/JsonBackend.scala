package hedgehog.controllers

import play.api.mvc.{Result, Controller}
import play.api.libs.json.Json

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
trait JsonBackend extends Controller {
  val commonJsonRecover: PartialFunction[Throwable, Result] = {
    //TODO: special cases for common hedgehog error
    case e: Exception => ServiceUnavailable(Json.obj(
      "error" -> true,
      "success" -> false,
      "error_class" -> e.getClass.toString,
      "message" -> e.getMessage
    ))
  }
}
