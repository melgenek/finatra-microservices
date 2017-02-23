package scalatalk.common.db

import javax.inject.Inject
import javax.sql.DataSource

import com.twitter.inject.annotations.Flag
import com.twitter.inject.utils.Handler
import org.flywaydb.core.Flyway

class FlywayWarmUp @Inject()(dataSource: DataSource, @Flag("package.name") packageName: String) extends Handler {

	override def handle(): Unit = {
		val flyway = new Flyway()
		flyway.setDataSource(dataSource)
		flyway.setLocations(s"$packageName.db.migration")
		flyway.migrate()


	}

}
