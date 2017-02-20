package scalatealk.tests

import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.Test

import scalatalk.speaker.SpeakerServer
import scalatalk.talk.TalkServer

class SpeakerServiceIntegrationService extends Test {

	val talkService = new EmbeddedHttpServer(new TalkServer,
		flags = Map(
			"db.name" -> "talks"
		)
	)

	val speakerService = new EmbeddedHttpServer(new SpeakerServer,
		flags = Map(
			"db.name" -> "speakers"
		)
	)

	override protected def afterEach(): Unit = {
		talkService.close()
		speakerService.close()
	}

	test("should start both servers") {
		talkService.assertHealthy()
		speakerService.assertHealthy()
	}

}
