package scalatalk.speaker.entity

import scalatalk.talk.entity.Talk

case class SpeakerData(
												id: String,
												name: String,
												bio: Option[String] = None,
												talks: Seq[Talk] = List()
											)

