package hedgehog

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object StringUtils {
   implicit class StringImplicitConverters(val string: String) {
       import scala.util.control.Exception._
       def toIntOpt = catching(classOf[NumberFormatException]) opt string.toInt
   }
}
