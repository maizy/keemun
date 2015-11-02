package keemun.clients.github.resource

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2015
 * See LICENSE.txt for details.
 */
trait RequestKey[I, O] extends Resource[I, O] {

  /**
   * function to compute String key from input params
   */
  def computeKey(params: Input): String
}
