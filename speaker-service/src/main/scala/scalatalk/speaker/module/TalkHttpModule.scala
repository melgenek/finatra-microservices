package scalatalk.speaker.module

import com.twitter.app.Flag

import scalatalk.common.module.LinkerdHttpClientModule

object TalkHttpModule extends LinkerdHttpClientModule {

	val talkService: Flag[String] = flag(name = "talk.service", default = "talk", help = "Talk service name")

	override def dest: String = linkerdLocation()

	override def defaultHeaders: Map[String, String] = super.defaultHeaders ++ Map(
		"Host" -> talkService()
	)

}
