package db.migration

import com.twitter.util.{Await, Future}

import scalatalk.common.db.{SlickMigration, UnmanagedDatabase}
import scalatalk.event.dao.EventDao
import scalatalk.event.entity.Event

class V2__AddSampleEvents extends SlickMigration {

	override def migrate(database: UnmanagedDatabase): Unit = {
		val eventDao = new EventDao(database)
		val f1 = eventDao.create(Event("event1", "Moonlight party"))
		val f2 = eventDao.create(Event("event2", "Brew fest"))
		Await.result(Future.join(f1, f2))
	}

}
