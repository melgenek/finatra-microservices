package scalatalk.common.db

import java.sql.Connection

import org.flywaydb.core.api.migration.jdbc.BaseJdbcMigration

abstract class SlickMigration extends BaseJdbcMigration {

	def migrate(database: UnmanagedDatabase)

	override def migrate(connection: Connection): Unit = {
		val database = new UnmanagedDatabase(connection)
		migrate(database)
		database.close()
	}

}
