package scalatalk.common.module

import javax.sql.DataSource

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import com.zaxxer.hikari.HikariDataSource

object HikariModule extends TwitterModule {

	@Provides
	@Singleton
	def dataSource: DataSource = {
		val dataSource = new HikariDataSource()
		dataSource.setJdbcUrl("jdbc:h2:mem:test1")
		dataSource.setDriverClassName("org.h2.Driver")
		dataSource
	}

}
