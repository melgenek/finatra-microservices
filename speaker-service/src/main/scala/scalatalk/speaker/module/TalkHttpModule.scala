package scalatalk.speaker.module

import com.twitter.app.Flag
import com.twitter.finatra.httpclient.modules.HttpClientModule

object TalkHttpModule extends HttpClientModule {

	val talkService: Flag[String] = flag(name = "talk.service", default = "localhost:8888", help = "Talk service location")

	override def dest: String = {
		info(s"Talk service is located here: ${talkService()}")
		talkService()
	}

}
