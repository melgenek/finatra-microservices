package scalatalk.talk.dao

import javax.inject.Inject

import com.twitter.util.Future
import slick.jdbc.JdbcBackend

import scalatalk.common.module.SlickDatabaseModule.SlickDatabaseApi._
import scalatalk.talk.entity.Talk
import scalatalk.common.util.FutureUtils._

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

	def getAll: Future[Seq[Talk]] = {
		db.run(talks.result).asTwitterFuture
	}

}
