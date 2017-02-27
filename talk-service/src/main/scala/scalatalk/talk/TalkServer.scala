package scalatalk.talk

import com.twitter.finatra.http.routing.HttpRouter

import scalatalk.common.server.ScalaTalkServer
import scalatalk.talk.controller.TalkController

object TalkServerMain extends TalkServer

class TalkServer extends ScalaTalkServer {

	override def configureHttp(router: HttpRouter): Unit = {
		super.configureHttp(router)
		router.add[TalkController]
	}

}
