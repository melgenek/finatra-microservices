package scalatalk.event.controller

import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scalatalk.event.service.EventService

class EventController @Inject()(eventService: EventService) extends Controller {

	get("/events") { req: Request =>
		eventService.findAllEvents();
	}

	get("/events/:id") { req: Request =>
		val eventId = req.getParam("id")
		eventService.findEventById(eventId);
	}


}
