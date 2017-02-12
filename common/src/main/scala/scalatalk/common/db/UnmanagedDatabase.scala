package scalatalk.common.db


import java.sql.Connection

import slick.jdbc.JdbcBackend.{BaseSession, DatabaseDef}
import slick.jdbc.{JdbcBackend, JdbcDataSource}
import slick.util.AsyncExecutor


class UnmanagedJdbcDataSource(conn: Connection) extends JdbcDataSource {
	def createConnection(): Connection = conn

	def close(): Unit = ()
}

class UnmanagedSession(database: DatabaseDef) extends BaseSession(database) {
	override def close(): Unit = ()
}

class UnmanagedDatabase(conn: Connection)
	extends JdbcBackend.DatabaseDef(new UnmanagedJdbcDataSource(conn), AsyncExecutor("AsyncExecutor.single", 1, -1)) {
	override def createSession() = new UnmanagedSession(this)
}
