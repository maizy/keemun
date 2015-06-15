package keemun.clients.github.resource

import scala.concurrent.{ExecutionContext, Future}

import keemun.clients.github.Config

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2015
 * See LICENSE.txt for details.
 */
abstract class Resource[I, O] (clientConfig: Config)(implicit protected val context: ExecutionContext)  {
  type Input = I
  type Output = O
  def get(params: I): Future[Output]
}
