package scalatalk.event

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer

import scalatest.common.FinatraFeatureTest

class EventServiceTest extends FinatraFeatureTest {

	override def server = new EmbeddedHttpServer(new EventServer)

	test("should get existing event by id") {
		server.httpGet(
			path = "/events/event1",
			andExpect = Ok,
			withJsonBody =
				"""{"id":"event1","name":"Moonlight party"}"""
		)
	}

	test("should get nobody when no speaker with id") {
		server.httpGet(
			path = "/events/event10",
			andExpect = NotFound
		)
	}

	test("should get all events") {
		server.httpGet(
			path = "/events",
			andExpect = Ok,
			withJsonBody =
				"""[{"id":"event1","name":"Moonlight party"},{"id":"event2","name":"Brew fest"}]"""
		)
	}

}
