package scalatalk.speaker

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer

import scalatest.common.FinatraFeatureTest

class SpeakerServiceTest extends FinatraFeatureTest {

	override def server = new EmbeddedHttpServer(new SpeakerServer)

	test("should get existing speaker by id") {
		server.httpGet(
			path = "/speakers/speaker1/simple",
			andExpect = Ok,
			withJsonBody =
				"""{"id":"speaker1","name":"Blue Elephant"}"""
		)
	}

	test("should get nobody when no speaker with id") {
		server.httpGet(
			path = "/speakers/speaker4/simple",
			andExpect = NotFound
		)
	}

}
