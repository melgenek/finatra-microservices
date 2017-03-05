package db.migration

import com.twitter.util.Await

import scalatalk.common.db.{SlickMigration, UnmanagedDatabase}
import scalatalk.event.dao.EventDao

class V1__CreateEventsTable extends SlickMigration {

	override def migrate(database: UnmanagedDatabase): Unit = {
		val eventDao = new EventDao(database)

		Await.result(eventDao.createTable)
	}

}
