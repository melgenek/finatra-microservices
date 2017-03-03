package scalatalk.event.service

import javax.inject.{Inject, Named, Singleton}

import com.twitter.finatra.httpclient.{HttpClient, RequestBuilder}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import com.twitter.util.Future

import scalatalk.event.dao.EventDao
import scalatalk.event.entity.Event
import scalatalk.speaker.entity.Speaker
import scalatalk.talk.entity.Talk

@Singleton
class EventService @Inject()(eventDao: EventDao,
														 mapper: FinatraObjectMapper,
														 @Named("talkClient") talkClient: HttpClient,
														 @Named("speakerClient") speakerClient: HttpClient) extends Logging {

	def findAllEvents(): Future[Seq[Event]] = eventDao.findAll

	def findEventById(eventId: String): Future[Option[Event]] = {
		eventDao.findById(eventId)
	}

	def findEventWithSpeakersById(eventId: String) = {
		for {
			eventById <- findEventById(eventId)
//			g = getEventData(eventId)
		} yield {

		}
	}

//	private def getEventData(eventId: String): Future[Seq[(Speaker, Talk)]] = {
//		val futures: Future[Equals] = talkClient.execute(RequestBuilder.get(s"/talks/events/$eventId")).map(talkResponse => {
//			if (talkResponse.statusCode == 200) {
//				mapper.parse[Seq[Talk]](talkResponse.contentString).map(talk => {
//					getSpeakerWithTalk(talk)
//				})
//			} else {
//				None
//			}
//		})
////		Future.collect(futures)
//	}

	private def getSpeakerWithTalk(talk: Talk): Future[Option[(Speaker, Talk)]] = {
		talkClient.execute(RequestBuilder.get(s"/speakers/${talk.speaker}")).map(speakerResponse => {
			if (speakerResponse.statusCode == 200) {
				val speaker = mapper.parse[Speaker](speakerResponse.contentString)
				Some((speaker, talk))
			} else {
				None
			}
		})
	}
}
