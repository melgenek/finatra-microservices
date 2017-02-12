package scalatalk.common.util

import com.twitter.bijection.Conversion._
import com.twitter.bijection.twitter_util.UtilBijections.twitter2ScalaFuture
import com.twitter.{util => twitter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FutureUtils {

	implicit class ScalaToTwitterFuture[T](f: Future[T]) {
		def asTwitterFuture: twitter.Future[T] = f.as[twitter.Future[T]]
	}

}
