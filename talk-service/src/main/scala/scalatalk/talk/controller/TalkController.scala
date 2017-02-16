package scalatalk.talk.controller

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scalatalk.talk.dao.TalkDao

class TalkController @Inject()(talkDao: TalkDao) extends Controller {

	get("/talks") { _: Request =>
		talkDao.getAll
	}

	get("/talks/:id") { req: Request =>
		val talkId = req.getParam("id")
		talkDao.findById(talkId)
	}

	get("/talks/speakers/:speakerId") { req: Request =>
		val speakerId = req.getParam("speakerId")
		talkDao.findBySpeakerId(speakerId)
	}

	get("/talks/events/:eventId") { req: Request =>
		val eventId = req.getParam("eventId")
		talkDao.findByEventId(eventId)
	}

}
