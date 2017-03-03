package scalatalk.event

import com.google.inject.Stage
import com.twitter.finatra.http.EmbeddedHttpServer

import scalatest.common.FinatraFeatureTest

class EventServiceStartupTest extends FinatraFeatureTest {

	override val server = new EmbeddedHttpServer(
		stage = Stage.PRODUCTION,
		twitterServer = new EventServer
	)

	test("Server should start") {
		server.assertHealthy()
	}

}
