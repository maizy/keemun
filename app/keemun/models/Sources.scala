package keemun.models

import scala.collection.JavaConversions._

import play.api.libs.json.{Json, JsValue, Writes}
import play.api.Configuration
import com.typesafe.config.{ConfigValueType, ConfigObject, ConfigList, Config}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Sources(configuration: Configuration) {
  lazy val accountsSettings: Seq[AccountSettings] =
    configuration.getList("github.sources") match {
      case Some(sourcesConfig: ConfigList) =>
        sourcesConfig.map {
            cfg => cfg.valueType() match {
              case ConfigValueType.STRING => Some(AccountSettings(cfg.unwrapped.asInstanceOf[String]))
              case ConfigValueType.OBJECT => Some(AccountSettings(cfg.asInstanceOf[ConfigObject]))
              case _ => None
            }
          }.flatten.toList
      case _ => throw new ConfigError("github.sources config path not defined")
    }
}


class AccountSettings(
   val account: Account,
   val includePrivateRepos: Boolean = false)
{

  def canEqual(other: Any): Boolean = other.isInstanceOf[AccountSettings]

  override def equals(other: Any): Boolean = other match {
    case that: AccountSettings =>
      (that canEqual this) &&
        account == that.account &&
        includePrivateRepos == that.includePrivateRepos
    case _ => false
  }

  override def hashCode(): Int =
    Seq(account, includePrivateRepos).map(_.hashCode).foldLeft(0)((a, b) => 31 * a + b)

  override def toString: String =
    s"AccountSettings($account, includePrivateRepos=$includePrivateRepos)"
}


object AccountSettings {
  def apply(name: String) = {
    new AccountSettings(GithubUser(name=name))
  }

  def apply(config: ConfigObject): AccountSettings = AccountSettings(config.toConfig)

  def apply(config: Config): AccountSettings = {
    //TODO: use some typesafe.config wrapper
    val isOrg = try {
      config.getBoolean("org")
    } catch {
      case e: com.typesafe.config.ConfigException => false
    }
    val name = try {
      config.getString("name")
    } catch {
      case e: com.typesafe.config.ConfigException =>
        throw new ConfigError(s"wrong 'name' for source: $config")
    }
    val includePrivate = try {
      config.getBoolean("include_private_repos")
    } catch {
      case e: com.typesafe.config.ConfigException => false
    }
    if (isOrg) {
      new AccountSettings(GithubOrg(name), includePrivate)
    } else {
      new AccountSettings(GithubUser(name), includePrivate)
    }
  }

  implicit val accountSettingWrites = new Writes[AccountSettings] {
    def writes(settings: AccountSettings): JsValue = {
      import AccountJson.accountWrites
      Json.obj(
        "include_private_repos" -> settings.includePrivateRepos,
        "account" -> settings.account
      )
    }
  }

}
