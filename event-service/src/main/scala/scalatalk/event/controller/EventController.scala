package scalatalk.event.controller

import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scalatalk.event.service.EventService

class EventController @Inject()(eventService: EventService) extends Controller {

	get("/events") { req: Request =>
		info("Getting all events")
		eventService.findAllEvents();
	}

	get("/events/:id") { req: Request =>
		val eventId = req.getParam("id")
		info(s"Getting event $eventId")
		eventService.findEventWithSpeakersById(eventId)
	}

}
