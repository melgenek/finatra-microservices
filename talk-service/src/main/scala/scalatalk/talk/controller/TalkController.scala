package scalatalk.talk.controller

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.Future

import scalatalk.talk.dao.TalkDao
import scalatalk.talk.entity.Talk

class TalkController @Inject()(talkDao: TalkDao) extends Controller {

	get("/talks") { _: Request =>
		talkDao.findAll
	}

	get("/talks/:id") { req: Request =>
		val talkId = req.getParam("id")
		talkDao.findById(talkId)
	}

	get("/talks/speakers/:speakerId") { req: Request =>
		val speakerId = req.getParam("speakerId")
		talkDao.findBySpeakerId(speakerId).flatMap(createResponse)
	}

	get("/talks/events/:eventId") { req: Request =>
		val eventId = req.getParam("eventId")
		talkDao.findByEventId(eventId).flatMap(createResponse)
	}

	private def createResponse(talks: Seq[Talk]): Future[ResponseBuilder#EnrichedResponse] = talks match {
		case l if l.nonEmpty => Future.value(response.ok(l))
		case _ => Future.value(response.notFound)
	}

}
