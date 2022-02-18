//import cats.effect.Sync
//import pureconfig._
////import pureconfig.generic.auto._
////import pureconfig.generic.auto.*
//import pureconfig.generic._
//import pureconfig.generic.derivation.default.*
//
//import AppConfig.*
//
////case class AppConfig(http: Http) derives ConfigReader
////case class AppConfig(http: Http)
//case class AppConfig(http: Http) derives ConfigReader
//
//object AppConfig {
//  case class Http(port: Int, host: String)
//
//  def loadConfig[F[_]: Sync]           = Sync[F].delay(ConfigSource.default.loadOrThrow[AppConfig])
//  def load[F[_]: Sync](): F[AppConfig] = ???
//}

import cats.effect.Sync
import AppConfig._
import com.typesafe.config.ConfigFactory

case class AppConfig(http: Http)

object AppConfig {
  case class Http(port: Int, host: String)

  def load[F[_]: Sync](): F[AppConfig] = Sync[F].delay {
    val cfg     = ConfigFactory.load()
    val httpCfg = cfg.getConfig("http")
    val http    = Http(port = httpCfg.getInt("port"), host = httpCfg.getString("host"))
    AppConfig(http)
  }
}
