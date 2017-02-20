package scalatalk.common.controller

import com.twitter.finatra.http.HttpServer

import scalatalk.common.db.FlywayWarmUp
import scalatalk.common.module.{ConfigModule, HikariModule, SlickDatabaseModule}

abstract class ScalaTalkServer extends HttpServer {

	override def modules = Seq(ConfigModule, HikariModule, SlickDatabaseModule)

	override def warmup(): Unit = {
		handle[FlywayWarmUp]()
	}

}

