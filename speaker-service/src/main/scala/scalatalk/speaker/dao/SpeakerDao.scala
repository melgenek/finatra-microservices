package scalatalk.speaker.dao

import javax.inject.Inject

import com.twitter.util.Future
import slick.jdbc.JdbcBackend

import scalatalk.common.module.SlickDatabaseModule.SlickDatabaseApi._
import scalatalk.common.util.FutureUtils._
import scalatalk.speaker.entity.Speaker

class SpeakerDao @Inject()(db: JdbcBackend.DatabaseDef) {

	private class Speakers(tag: Tag) extends Table[Speaker](tag, "speakers") {

		def id = column[String]("id", O.PrimaryKey)

		def name = column[String]("name")

		def bio = column[Option[String]]("bio")

		def * = (id, name, bio) <> (Speaker.tupled, Speaker.unapply)

	}

	private val speakers = TableQuery[Speakers]

	def createTable: Future[Unit] = {
		db.run(speakers.schema.create).asTwitterFuture
	}

	def create(speaker: Speaker): Future[Int] = {
		db.run(speakers += speaker).asTwitterFuture
	}

	def findById(speakerId: String): Future[Option[Speaker]] = {
		db.run {
			speakers.filter(_.id === speakerId).take(1).result.headOption
		}.asTwitterFuture
	}

}
