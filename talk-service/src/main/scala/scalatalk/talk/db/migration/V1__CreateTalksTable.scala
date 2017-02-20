package scalatalk.talk.db.migration

import java.sql.Connection

import com.twitter.util.Await

import scalatalk.common.db.{SlickMigration, UnmanagedDatabase}
import scalatalk.talk.dao.TalkDao

class V1__CreateTalksTable extends SlickMigration {

	override def migrate(database: UnmanagedDatabase): Unit = {
		val talkDao = new TalkDao(database)

		Await.result(talkDao.createTable)
	}

}
