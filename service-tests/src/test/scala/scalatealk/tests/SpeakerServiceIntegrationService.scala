package scalatealk.tests

import com.twitter.finagle.http.Status.Ok
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
			"db.name" -> "speakers",
			"linkerd.location" -> talkService.externalHttpHostAndPort
		)
	)

	override protected def afterAll(): Unit = {
		talkService.close()
		speakerService.close()
	}

	test("should start both servers") {
		talkService.assertHealthy()
		speakerService.assertHealthy()
	}

	test("should get speaker with talks") {
		speakerService.httpGet(
			path = "/speakers/speaker1",
			andExpect = Ok,
			withJsonBody =
				"""{"id":"speaker1","name":"Blue Elephant","talks":[{"id":"1","speaker":"speaker1","event":"event1","title":"Talk 1"}]}"""
		)
	}

}
