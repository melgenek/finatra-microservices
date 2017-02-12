package scalatalk.common.db

import javax.inject.Inject
import javax.sql.DataSource

import com.twitter.inject.utils.Handler
import org.flywaydb.core.Flyway

class FlywayWarmUp @Inject()(dataSource: DataSource) extends Handler {

	override def handle(): Unit = {
		val flyway = new Flyway()
		flyway.setDataSource(dataSource)
		flyway.migrate()
	}

}
