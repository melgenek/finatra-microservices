package scalatalk.speaker.service

import javax.inject.{Inject, Singleton}

import com.twitter.finatra.httpclient.{HttpClient, RequestBuilder}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.util.Future

import scalatalk.speaker.dao.SpeakerDao
import scalatalk.speaker.entity.{Speaker, SpeakerData}
import scalatalk.talk.entity.Talk

@Singleton
class SpeakerService @Inject()(speakerDao: SpeakerDao, talkClient: HttpClient, mapper: FinatraObjectMapper) {

	def findSpeakerById(speakerId: String): Future[Option[Speaker]] = {
		speakerDao.findById(speakerId)
	}

	def findSpeakerWithTalksById(speakerId: String): Future[Option[SpeakerData]] = {
		for {
			speakerById <- findSpeakerById(speakerId)
			talkResponse <- talkClient.execute(RequestBuilder.get(s"/talks/speakers/$speakerId"))
		} yield {
			speakerById match {
				case Some(speaker) =>
					if (talkResponse.statusCode == 200) {
						val talks = mapper.parse[List[Talk]](talkResponse.contentString)
						Some(SpeakerData(speaker.id, speaker.name, speaker.bio, talks))
					} else {
						Some(SpeakerData(speaker.id, speaker.name, speaker.bio))
					}
				case None => None
			}
		}
	}

}
