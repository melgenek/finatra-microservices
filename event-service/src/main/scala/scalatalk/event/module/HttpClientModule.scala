package scalatalk.event.module

import javax.inject.{Named, Singleton}

import com.google.inject.Provides
import com.twitter.app.Flag
import com.twitter.finatra.httpclient.{HttpClient, RichHttpClient}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.TwitterModule

object HttpClientModule extends TwitterModule {

	val talkServiceLocation: Flag[String] = flag(name = "talk.service.location", default = "localhost:8888", help = "Talk service location")
	val speakerServiceLocation: Flag[String] = flag(name = "speaker.service.location", default = "localhost:8889", help = "Speaker service location")

	val talkServiceName: Flag[String] = flag(name = "talk.service.name", default = "talk", help = "Talk service name")
	val speakerServiceName: Flag[String] = flag(name = "speaker.service.name", default = "speaker", help = "Speaker service name")

	@Provides
	@Singleton
	@Named("talkClient")
	def talkClient(mapper: FinatraObjectMapper): HttpClient = {
		new HttpClient(
			httpService = RichHttpClient.newClientService(dest = talkServiceLocation()),
			mapper = mapper,
			defaultHeaders = Map(
				"Host" -> talkServiceName()
			)
		)
	}

	@Provides
	@Singleton
	@Named("speakerClient")
	def speakerClient(mapper: FinatraObjectMapper): HttpClient = {
		new HttpClient(
			httpService = RichHttpClient.newClientService(dest = speakerServiceLocation()),
			mapper = mapper,
			defaultHeaders = Map(
				"Host" -> speakerServiceName()
			)
		)
	}

}
