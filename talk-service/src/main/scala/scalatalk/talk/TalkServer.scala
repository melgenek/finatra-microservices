package scalatalk.talk

import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.TwitterModule

import scalatalk.common.module.BasePackageConfig
import scalatalk.common.server.ScalaTalkServer
import scalatalk.talk.controller.TalkController

object TalkServerMain extends TalkServer

class TalkServer extends ScalaTalkServer {

	override def modules: Seq[TwitterModule] = Seq(BasePackageConfig("scalatalk.talk")) ++ super.modules

	override def configureHttp(router: HttpRouter): Unit = {
		router.add[TalkController]
	}

}
