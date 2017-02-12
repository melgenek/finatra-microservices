package scalatalk.common.module

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import slick.jdbc.H2Profile.api._
import javax.sql.DataSource

object SlickDatabaseModule extends TwitterModule {

	type SlickDatabase = slick.jdbc.H2Profile.api.Database

	val SlickDatabaseApi = slick.jdbc.H2Profile.api

	@Singleton
	@Provides
	def database(dataSource: DataSource): SlickDatabase =
		Database.forDataSource(dataSource, AsyncExecutor("AsyncExecutor.simple", 5, -1))

}
