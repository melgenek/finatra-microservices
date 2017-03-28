package scalatalk.common.filter

import java.util.Base64

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.tracing.{Trace, TraceId}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.{Future, Return}

import scalatalk.common.context.Linkerd

class LinkerdFilter extends SimpleFilter[Request, Response] {

	override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
		val trace = request.headerMap.get("l5d-ctx-trace")
		if (trace.isDefined) {
			val lTrace = trace.get
			val traceBytes = Base64.getDecoder.decode(lTrace)
			TraceId.deserialize(traceBytes) match {
				case Return(traceId) => Trace.letId(traceId) {
					Linkerd.storeTrace(lTrace) {
						service(request)
					}
				}
				case _ => Linkerd.storeTrace(lTrace) {
					service(request)
				}
			}

		} else {
			service(request)
		}
	}

}
