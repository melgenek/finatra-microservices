package scalatalk.common.filter

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future
import org.slf4j.MDC

import scalatalk.common.context.Linkerd

class LinkerdFilter extends SimpleFilter[Request, Response] {

	override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
		val trace = request.headerMap.get("l5d-ctx-trace")
		if (trace.isDefined) {
			val lTrace = trace.get
			MDC.put("lTrace", lTrace)
			Linkerd.storeTrace(lTrace) {
				service(request)
			}
		} else {
			service(request)
		}
	}

}
