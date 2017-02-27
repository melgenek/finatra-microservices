package scalatalk.speaker

import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.TwitterModule

import scalatalk.common.server.ScalaTalkServer
import scalatalk.speaker.controller.SpeakerController
import scalatalk.speaker.module.TalkHttpModule

object SpeakerServerMain extends SpeakerServer

class SpeakerServer extends ScalaTalkServer {

	override def modules: Seq[TwitterModule] = Seq(TalkHttpModule) ++ super.modules

	override protected def configureHttp(router: HttpRouter): Unit = {
		router.add[SpeakerController]
	}

}
