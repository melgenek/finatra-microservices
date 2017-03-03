package scalatalk.talk.dao

import javax.inject.Inject

import com.twitter.util.Future
import slick.jdbc.JdbcBackend

import scalatalk.common.module.SlickDatabaseModule.SlickDatabaseApi._
import scalatalk.common.util.FutureUtils._
import scalatalk.talk.entity.Talk

class TalkDao @Inject()(db: JdbcBackend.DatabaseDef) {

	private class Talks(tag: Tag) extends Table[Talk](tag, "talks") {

		def id = column[String]("id", O.PrimaryKey)

		def speaker = column[String]("speaker")

		def event = column[String]("event")

		def title = column[Option[String]]("title")

		def desc = column[Option[String]]("desc")

		def video = column[Option[String]]("video")

		def * = (id, speaker, event, title, desc, video) <> (Talk.tupled, Talk.unapply)

	}

	private val talks = TableQuery[Talks]

	def createTable: Future[Unit] = {
		db.run(talks.schema.create).asTwitterFuture
	}

	def create(talk: Talk): Future[Int] = {
		db.run(talks += talk).asTwitterFuture
	}

	def findAll: Future[Seq[Talk]] = {
		db.run(talks.result).asTwitterFuture
	}

	def findById(talkId: String): Future[Option[Talk]] = {
		db.run {
			talks.filter(_.id === talkId).take(1).result.headOption
		}.asTwitterFuture
	}

	def findBySpeakerId(speakerId: String): Future[Seq[Talk]] = {
		db.run {
			talks.filter(_.speaker === speakerId).take(1).result
		}.asTwitterFuture
	}

	def findByEventId(eventId: String): Future[Seq[Talk]] = {
		db.run {
			talks.filter(_.event === eventId).take(1).result
		}.asTwitterFuture
	}

}
