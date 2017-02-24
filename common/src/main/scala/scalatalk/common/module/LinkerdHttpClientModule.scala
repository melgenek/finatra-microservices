package scalatalk.common.module

import com.twitter.app.Flag
import com.twitter.finatra.httpclient.modules.HttpClientModule

abstract class LinkerdHttpClientModule extends HttpClientModule {

	val linkerdLocation: Flag[String] = flag(name = "linkerd.location", default = "localhost:4140", help = "Linkerd location")

}
