package scalatalk.common.context

import com.twitter.finagle.context.Contexts

object Linkerd {

	private val Trace = new Contexts.local.Key[String]()

	def storeTrace[T](trace: String)(f: => T): T =
		Contexts.local.let(Trace, trace) {
			f
		}

	def headers(): (String, String) = ("l5d-ctx-trace", Contexts.local.get(Trace).getOrElse(""))

}
