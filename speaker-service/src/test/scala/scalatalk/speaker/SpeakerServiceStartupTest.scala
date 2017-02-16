package scalatalk.speaker

import com.google.inject.Stage
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class SpeakerServiceStartupTest extends FeatureTest {

	override val server = new EmbeddedHttpServer(
		stage = Stage.PRODUCTION,
		twitterServer = new SpeakerServer
	)

	test("Server should start") {
		server.assertHealthy()
	}

}
