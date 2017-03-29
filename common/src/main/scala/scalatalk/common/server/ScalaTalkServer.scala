package scalatalk.common.server

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import com.codahale.metrics.graphite.{Graphite, GraphiteReporter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.metrics.MetricsStatsReceiver
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

import scalatalk.common.db.FlywayWarmUp
import scalatalk.common.filter.LinkerdFilter
import scalatalk.common.module.{HikariModule, SlickDatabaseModule}


abstract class ScalaTalkServer extends HttpServer {

	override def modules = Seq(HikariModule, SlickDatabaseModule)

	private val graphite = new Graphite(new InetSocketAddress("localhost", 2003))
	private val reporter = GraphiteReporter.forRegistry(MetricsStatsReceiver.metrics)
		.prefixedWith("finagle-service.example.com")
		.convertRatesTo(TimeUnit.SECONDS)
		.convertDurationsTo(TimeUnit.MILLISECONDS)
		.build(graphite)

	override protected def configureHttp(router: HttpRouter): Unit = {
		router
			.filter[LinkerdFilter]
			.filter[LoggingMDCFilter[Request, Response]]
			.filter[TraceIdMDCFilter[Request, Response]]
			.filter[CommonFilters]

		onExit {
			reporter.stop()
		}
	}

	override def warmup(): Unit = {
		handle[FlywayWarmUp]()
		reporter.start(5, TimeUnit.SECONDS)
	}

}

