package scalatalk.speaker.service

import javax.inject.{Inject, Singleton}

import com.twitter.finatra.httpclient.{HttpClient, RequestBuilder}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.util.Future

import scalatalk.speaker.dao.SpeakerDao
import scalatalk.speaker.entity.{Speaker, SpeakerData}
import scalatalk.talk.entity.Talk

@Singleton
class SpeakerService @Inject()(speakerDao: SpeakerDao, httpClient: HttpClient, mapper: FinatraObjectMapper) {

	def findSpeakerById(speakerId: String): Future[Speaker] = {
		speakerDao.findById(speakerId).flatMap {
			case Some(speaker) => Future.value(speaker)
			case None => Future.value(null)
		}
	}

	def findSpeakerWithTalksById(speakerId: String): Future[SpeakerData] = {
		for {
			speaker <- findSpeakerById(speakerId)
			talkResponse <- httpClient.execute(RequestBuilder.get(s"/talks/speakers/$speakerId"))
		} yield {
			val talks = mapper.parse[List[Talk]](talkResponse.contentString)
			SpeakerData(speaker.id, speaker.name, speaker.bio, talks)
		}
	}

}
