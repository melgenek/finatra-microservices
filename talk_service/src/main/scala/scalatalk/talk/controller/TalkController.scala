package scalatalk.talk.controller

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scalatalk.talk.dao.TalkDao

class TalkController @Inject()(talkDao: TalkDao) extends Controller {

	get("/talks") { _: Request =>
		talkDao.getAll
	}

}
