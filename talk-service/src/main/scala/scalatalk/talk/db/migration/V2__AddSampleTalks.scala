package scalatalk.talk.db.migration

import com.twitter.util.{Await, Future}

import scalatalk.common.db.{SlickMigration, UnmanagedDatabase}
import scalatalk.talk.dao.TalkDao
import scalatalk.talk.entity.Talk

class V2__AddSampleTalks extends SlickMigration {

	override def migrate(database: UnmanagedDatabase): Unit = {
		val talkDao = new TalkDao(database)
		val f1 = talkDao.create(Talk("1", "speaker1", "event1", Some("Talk 1")))
		val f2 = talkDao.create(Talk("2", "speaker2", "event2", Some("Talk 2")))
		Await.result(Future.join(f1, f2))
	}

}
