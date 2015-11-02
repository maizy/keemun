package keemun.clients.github.resource

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.reflect.ClassTag

import play.api.Logger
import play.api.cache.Cache

/**
 * Add caching for resource
 *
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2015
 * See LICENSE.txt for details.
 *
 * TODO: for cache client use DI & CacheAPI instances (play 2.4+)
 */
trait ResourceAsyncCache[I, O] extends Resource[I, O] with RequestKey[I, O] {

  implicit protected val classTag: ClassTag[Output]
  protected val ttl: Duration

  protected val logger: Option[Logger] = None

  private var missesCount: Int = _
  private var hitsCount: Int = _

  def misses(): Int = missesCount
  def hits(): Int = hitsCount

  abstract override def get(params: Input): Future[Output] = {
    import play.api.Play.current  //TODO: di
    val key = computeKey(params)
    val fromCache: Option[Output] = Cache.getAs[Output](key)
    if (fromCache.isDefined) {
      logger.foreach { _.debug(s"get data from cache for $key") }
      hitsCount += 1
      Future.successful(fromCache.get)
    } else {
      missesCount += 1
      logger.foreach { _.debug(s"get data from resource for $key") }
      val future = super.get(params)
      future onComplete {
        case Success(v) =>
          logger.foreach{ _.debug(s"set data to cache for $key") }
          Cache.set(key, v, expiration = ttl)

        case Failure(e: Throwable) =>
          logger.foreach{ _.debug(s"unable to get data for $key, remove data from cache") }
          Cache.remove(key)
      }
      future
    }
  }
}
