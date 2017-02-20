package scalatalk.speaker

import com.google.inject.Stage
import com.twitter.finatra.http.EmbeddedHttpServer

import scalatest.common.FinatraFeatureTest

class SpeakerServiceStartupTest extends FinatraFeatureTest {

	override val server = new EmbeddedHttpServer(
		stage = Stage.PRODUCTION,
		twitterServer = new SpeakerServer
	)

	test("Server should start") {
		server.assertHealthy()
	}

}
