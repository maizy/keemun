package hedgehog.controllers

import play.api.mvc.{Controller, AnyContent, Request}
import hedgehog.views.ViewContext

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
trait WithViewContext extends Controller {
  implicit def viewContext (implicit request: Request[AnyContent]) = {
    new ViewContext(request, lang)
  }
}
