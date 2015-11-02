package keemun.clients.github.resource

import scala.collection.concurrent.TrieMap
import scala.concurrent.duration._
import scala.concurrent.{Promise, Future}
import scala.util.{Failure, Success}
import scala.reflect.ClassTag

import play.api.Logger
import play.api.cache.Cache

/**
 * Make the same parallel requests run as one real request
 *
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2015
 * See LICENSE.txt for details.
 */
trait ParallelAsOne[I, O] extends Resource[I, O] with RequestKey[I, O] {

  private val nowFetched = TrieMap[String, Seq[Promise[Output]]]()

  private def isFetchNeeded(key: String, promise: Promise[Output]): Boolean = {
    this.synchronized {
      val promises = this.popCurrentPromises(key)
      val needFetch = promises.length == 0
      nowFetched(key) = promise +: promises
      needFetch
    }
  }

  private def finishPromises(key: String, func: Seq[Promise[Output]] => Unit) {
    popCurrentPromises(key).foreach(func)
  }

  private def popCurrentPromises(key: String): Seq[Promise[Output]] = {
    this.synchronized {
      val promises = nowFetched.getOrElse(key, Seq[Promise[Output]]())
      nowFetched.remove(key)
      promises
    }
  }

  protected val logger: Option[Logger] = None

  abstract override def get(params: Input): Future[Output] = {
    import play.api.Play.current  //TODO: di
    val key = computeKey(params)

  }
}
