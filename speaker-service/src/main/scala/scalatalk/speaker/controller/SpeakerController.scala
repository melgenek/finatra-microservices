package scalatalk.speaker.controller

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scalatalk.speaker.dao.SpeakerDao

class SpeakerController @Inject()(speakerDao: SpeakerDao) extends Controller {

	get("/speakers/:id/simple") { req: Request =>
		val speakerId = req.getParam("id")
		speakerDao.findById(speakerId)
	}

}
