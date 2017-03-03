package scalatalk.event.entity

case class Event(
									id: String,
									name: String,
									desc: Option[String] = None
								)
