package test

import org.scalatest._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hedgehog.models.{GithubUser, AccountSettings}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
@RunWith(classOf[JUnitRunner])
class AccountSettingsSuite extends FunSuite with Matchers {

  test("hash equals for the same content") {
    val user1 = GithubUser("user")
    val user2 = GithubUser("user")
    assert(user1.hashCode === user2.hashCode)
    val accountSettings1 = new AccountSettings(user1, true)
    val accountSettings2 = new AccountSettings(user2, true)
    assert(accountSettings1.hashCode === accountSettings2.hashCode)
    val accountSettings3 = new AccountSettings(user1, false)
    assert(accountSettings1.hashCode !== accountSettings3.hashCode)
  }
}
