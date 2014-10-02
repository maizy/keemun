import play.api._

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object Global extends GlobalSettings {
  override def configuration = Configuration.from(
    Map("ws" -> Map("useragent" -> s"keemun/${keemun.Version.SYMBOLIC}"))
  )
}
