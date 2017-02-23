package scalatalk.common.server

import com.twitter.finatra.http.HttpServer

import scalatalk.common.db.FlywayWarmUp
import scalatalk.common.module.{HikariModule, SlickDatabaseModule}

abstract class ScalaTalkServer extends HttpServer {

	override def modules = Seq(HikariModule, SlickDatabaseModule)

	override def warmup(): Unit = {
		handle[FlywayWarmUp]()
	}

}

