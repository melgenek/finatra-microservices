package scalatalk.speaker.db.migration

import com.twitter.util.Await

import scalatalk.common.db.{SlickMigration, UnmanagedDatabase}
import scalatalk.speaker.dao.SpeakerDao

class V1__CreateSpeakersTable extends SlickMigration {

	override def migrate(database: UnmanagedDatabase): Unit = {
		val speakerDao = new SpeakerDao(database)

		Await.result(speakerDao.createTable)
	}

}
