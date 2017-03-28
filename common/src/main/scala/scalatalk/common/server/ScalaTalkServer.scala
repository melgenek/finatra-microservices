package scalatalk.common.server

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

import scalatalk.common.db.FlywayWarmUp
import scalatalk.common.filter.LinkerdFilter
import scalatalk.common.module.{HikariModule, SlickDatabaseModule}


abstract class ScalaTalkServer extends HttpServer {

	override def modules = Seq(HikariModule, SlickDatabaseModule)

	override protected def configureHttp(router: HttpRouter): Unit = {
		router
			.filter[LinkerdFilter]
			.filter[LoggingMDCFilter[Request, Response]]
			.filter[TraceIdMDCFilter[Request, Response]]
			.filter[CommonFilters]
	}

	override def warmup(): Unit = {
		handle[FlywayWarmUp]()
	}

}

