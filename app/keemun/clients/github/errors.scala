package keemun.clients.github

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Error(msg: String = null, cause: Throwable = null) extends Exception(msg, cause)
class FetchError(msg: String = null, cause: Throwable = null) extends Error(msg, cause)
class FormatError(msg: String = null, cause: Throwable = null) extends Error(msg, cause)
