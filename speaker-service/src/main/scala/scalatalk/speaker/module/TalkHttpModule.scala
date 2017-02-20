package scalatalk.speaker.module

import com.twitter.finatra.httpclient.modules.HttpClientModule

object TalkHttpModule extends HttpClientModule {
	override def dest: String = "localhost:8888"
}
