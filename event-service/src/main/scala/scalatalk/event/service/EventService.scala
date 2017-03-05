package scalatalk.event.service

import javax.inject.{Inject, Named, Singleton}

import com.twitter.finagle.http.Response
import com.twitter.finatra.httpclient.{HttpClient, RequestBuilder}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import com.twitter.util.Future

import scalatalk.event.dao.EventDao
import scalatalk.event.entity.{Event, EventData}
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

	def findEventWithSpeakersById(eventId: String): Future[Option[EventData]] = {
		for {
			eventById <- findEventById(eventId)
			eventData <- getEventData(eventId)
		} yield {
			eventById match {
				case Some(event) =>
					Some(EventData(
						id = event.id,
						name = event.name,
						desc = event.desc,
						eventData = eventData
					))
				case None => None
			}
		}
	}

	private def getEventData(eventId: String): Future[Map[String, Seq[Talk]]] = {
		for {
			speakerToTalkPairs <- talkClient.execute(RequestBuilder.get(s"/talks/events/$eventId")).flatMap(handleTalkResponse)
		} yield {
			speakerToTalkPairs
				.filter {
					case Some(_) => true
					case None => false
				}
				.map(_.get)
				.groupBy(_._1)
				.map { case (speaker, tuple) => (speaker.id, tuple.map(_._2)) }
		}
	}

	private def handleTalkResponse(talkResponse: Response): Future[Seq[Option[(Speaker, Talk)]]] = {
		if (talkResponse.statusCode == 200) {
			val talks = mapper.parse[Seq[Talk]](talkResponse.contentString).map(talk => {
				getSpeakerWithTalk(talk)
			})
			Future.collect(talks)
		} else {
			Future.value(Seq())
		}
	}

	private def getSpeakerWithTalk(talk: Talk): Future[Option[(Speaker, Talk)]] = {
		speakerClient.execute(RequestBuilder.get(s"/speakers/${talk.speaker}/simple")).map(speakerResponse => {
			if (speakerResponse.statusCode == 200) {
				val speaker = mapper.parse[Speaker](speakerResponse.contentString)
				Some((speaker, talk))
			} else {
				None
			}
		})
	}

}
