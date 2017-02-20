package scalatalk.talk

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer

import scalatest.common.FinatraFeatureTest

class TalkServiceTest extends FinatraFeatureTest {

	override def server = new EmbeddedHttpServer(new TalkServer)

	test("should get all talks") {
		server.httpGet(
			path = "/talks",
			andExpect = Ok,
			withJsonBody =
				"""
				[
					{"id":"1","speaker":"speaker1","event":"event1","title":"Talk 1"},
					{"id":"2","speaker":"speaker2","event":"event2","title":"Talk 2"}
				]
				"""
		)
	}

	test("should get existing talk by id") {
		server.httpGet(
			path = "/talks/1",
			andExpect = Ok,
			withJsonBody =
				"""{"id":"1","speaker":"speaker1","event":"event1","title":"Talk 1"}"""
		)
	}

	test("should get nothing when no talk with id") {
		server.httpGet(
			path = "/talks/3",
			andExpect = NotFound
		)
	}

	test("should get existing talk by speaker id") {
		server.httpGet(
			path = "/talks/speakers/speaker2",
			andExpect = Ok,
			withJsonBody =
				"""[{"id":"2","speaker":"speaker2","event":"event2","title":"Talk 2"}]"""
		)
	}

	test("should get nothing when no talks for speaker") {
		server.httpGet(
			path = "/talks/speakers/speaker3",
			andExpect = NotFound
		)
	}

	test("should get existing talk by event id") {
		server.httpGet(
			path = "/talks/events/event2",
			andExpect = Ok,
			withJsonBody =
				"""[{"id":"2","speaker":"speaker2","event":"event2","title":"Talk 2"}]"""
		)
	}

	test("should get nothing when no talks for event") {
		server.httpGet(
			path = "/talks/events/event3",
			andExpect = NotFound
		)
	}

}
