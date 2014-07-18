package hedgehog.clients.github

import scala.concurrent.{Future, Promise}
import hedgehog.models.{AccountSettings, Repo}


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Client(val config: Config, implicit val context: scala.concurrent.ExecutionContext) {

  def getRepos(accountSettings: AccountSettings): Future[Seq[Repo]] = {
    val finish = Promise[Seq[Repo]]()

    finish success List(new Repo("a", accountSettings.account),
                        new Repo("b", accountSettings.account))

    // finish failure new ConfigError("ou!")

    finish.future
  }
}
