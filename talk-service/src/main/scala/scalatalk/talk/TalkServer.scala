package scalatalk.talk

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

import scalatalk.common.db.FlywayWarmUp
import scalatalk.common.module.{HikariModule, SlickDatabaseModule}
import scalatalk.talk.controller.TalkController

object TalkServerMain extends TalkServer

class TalkServer extends HttpServer {

	override def modules = Seq(HikariModule, SlickDatabaseModule)

	override def configureHttp(router: HttpRouter): Unit = {
		router.add[TalkController]
	}

	override def warmup(): Unit = {
		handle[FlywayWarmUp]()
	}
}
