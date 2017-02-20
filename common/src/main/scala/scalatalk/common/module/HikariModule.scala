package scalatalk.common.module

import javax.sql.DataSource

import com.google.inject.{Provides, Singleton}
import com.twitter.app.Flag
import com.twitter.inject.TwitterModule
import com.zaxxer.hikari.HikariDataSource

object HikariModule extends TwitterModule {

	val dbName: Flag[String] = flag(name = "db.name", default = "test", help = "Database name")

	@Provides
	@Singleton
	def dataSource: DataSource = {
		info(s"Database with name ${dbName()} is starting")
		val dataSource = new HikariDataSource()
		dataSource.setJdbcUrl(s"jdbc:h2:mem:${dbName()}")
		dataSource.setDriverClassName("org.h2.Driver")
		dataSource
	}

}
