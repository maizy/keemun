package hedgehog.controllers

import play.api.mvc.{Controller, Request, AnyContent}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
trait PostArguments extends Controller {

  def postArgSeq(name: String)(implicit request: Request[AnyContent]): Option[Seq[String]] =
    request.body.asFormUrlEncoded.flatMap(_.get(name))

  def postArg(name: String)(implicit request: Request[AnyContent]): Option[String] =
    postArgSeq(name).map(_.last)
}
