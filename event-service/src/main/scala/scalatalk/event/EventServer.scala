package scalatalk.event

import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.TwitterModule

import scalatalk.common.server.ScalaTalkServer
import scalatalk.event.controller.EventController
import scalatalk.event.module.HttpClientModule

object EventServerMain extends EventServer

class EventServer extends ScalaTalkServer {

	override def modules: Seq[TwitterModule] = Seq(HttpClientModule) ++ super.modules

	override def configureHttp(router: HttpRouter): Unit = {
		super.configureHttp(router)
		router.add[EventController]
	}

}
