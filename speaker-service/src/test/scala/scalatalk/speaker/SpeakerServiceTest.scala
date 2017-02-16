package scalatalk.speaker

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class SpeakerServiceTest extends FeatureTest {

	override def server = new EmbeddedHttpServer(new SpeakerServer)

	test("should get existing speaker by id") {
		server.httpGet(
			path = "/speakers/speaker1/simple",
			andExpect = Ok,
			withBody =
				"""{"id":"speaker1","name":"Blue Elephant"}""".stripMargin
		)
	}

	test("should get nobody when no speaker with id") {
		server.httpGet(
			path = "/speakers/speaker3/simple",
			andExpect = NotFound
		)
	}

}
