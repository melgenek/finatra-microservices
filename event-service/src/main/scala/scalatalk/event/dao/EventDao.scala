package scalatalk.event.dao

import javax.inject.Inject

import com.twitter.util.Future
import slick.jdbc.JdbcBackend

import scalatalk.common.module.SlickDatabaseModule.SlickDatabaseApi._
import scalatalk.common.util.FutureUtils._
import scalatalk.event.entity.Event

class EventDao @Inject()(db: JdbcBackend.DatabaseDef) {

	private class Events(tag: Tag) extends Table[Event](tag, "events") {

		def id = column[String]("id", O.PrimaryKey)

		def name = column[String]("name")

		def desc = column[Option[String]]("desc")

		def * = (id, name, desc) <> (Event.tupled, Event.unapply)

	}

	private val events = TableQuery[Events]

	def createTable: Future[Unit] = {
		db.run(events.schema.create).asTwitterFuture
	}

	def create(event: Event): Future[Int] = {
		db.run(events += event).asTwitterFuture
	}

	def findAll: Future[Seq[Event]] = {
		db.run(events.result).asTwitterFuture
	}

	def findById(eventId: String): Future[Option[Event]] = {
		db.run {
			events.filter(_.id === eventId).take(1).result.headOption
		}.asTwitterFuture
	}

}
