package scalatalk.speaker

import com.twitter.finatra.http.routing.HttpRouter

import scalatalk.common.controller.ScalaTalkServer
import scalatalk.speaker.controller.SpeakerController

object SpeakerServerMain extends SpeakerServer

class SpeakerServer extends ScalaTalkServer {

	override protected def configureHttp(router: HttpRouter): Unit = {
		router.add[SpeakerController]
	}

}
