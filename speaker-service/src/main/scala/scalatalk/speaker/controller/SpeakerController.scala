package scalatalk.speaker.controller

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scalatalk.speaker.service.SpeakerService

class SpeakerController @Inject()(speakerService: SpeakerService) extends Controller {

	get("/speakers/:id/simple") { req: Request =>
		val speakerId = req.getParam("id")
		speakerService.findSpeakerById(speakerId)
	}

	get("/speakers/:id") { req: Request =>
		val speakerId = req.getParam("id")
		speakerService.findSpeakerWithTalksById(speakerId)
	}


}
