package scalatalk.speaker.module

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.app.Flag
import com.twitter.finatra.httpclient.{HttpClient, RichHttpClient}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.TwitterModule

object HttpClientModule extends TwitterModule {

	val talkServiceLocation: Flag[String] = flag(name = "talk.service.location", default = "localhost:8888", help = "Talk service location")

	val talkServiceName: Flag[String] = flag(name = "talk.service.name", default = "talk", help = "Talk service name")

	@Provides
	@Singleton
	def talkClient(mapper: FinatraObjectMapper): HttpClient = {
		new HttpClient(
			httpService = RichHttpClient.newClientService(dest = talkServiceLocation()),
			mapper = mapper,
			defaultHeaders = Map(
				"Host" -> talkServiceName()
			)
		)
	}

}
