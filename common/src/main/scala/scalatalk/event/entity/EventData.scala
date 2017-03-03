package scalatalk.event.entity

import scalatalk.speaker.entity.Speaker

case class EventData(
											id: String,
											name: String,
											desc: Option[String] = None,
											speakers: Seq[Speaker]
										)

