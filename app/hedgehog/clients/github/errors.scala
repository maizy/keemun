package hedgehog.clients.github

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Error(msg: String) extends Exception(msg)
class FetchError(msg: String) extends Exception(msg)
