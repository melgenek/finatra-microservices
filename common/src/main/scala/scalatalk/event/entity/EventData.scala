package scalatalk.event.entity

import scalatalk.speaker.entity.Speaker
import scalatalk.talk.entity.Talk

case class EventData(
											id: String,
											name: String,
											desc: Option[String] = None,
											eventData: Map[String, Seq[Talk]] = Map.empty[String, Seq[Talk]]
										)

