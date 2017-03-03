package scalatalk.speaker

import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.TwitterModule

import scalatalk.common.server.ScalaTalkServer
import scalatalk.speaker.controller.SpeakerController
import scalatalk.speaker.module.HttpClientModule

object SpeakerServerMain extends SpeakerServer

class SpeakerServer extends ScalaTalkServer {

	override def modules: Seq[TwitterModule] = Seq(HttpClientModule) ++ super.modules

	override protected def configureHttp(router: HttpRouter): Unit = {
		super.configureHttp(router)
		router.add[SpeakerController]
	}

}
