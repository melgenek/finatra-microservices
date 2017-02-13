package scalatalk.talk

import com.google.inject.Stage
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class TalkServiceStartupTest extends FeatureTest {

	override val server = new EmbeddedHttpServer(
		stage = Stage.PRODUCTION,
		twitterServer = new TalkServer
	)

	test("Server should start") {
		server.assertHealthy()
	}

}
