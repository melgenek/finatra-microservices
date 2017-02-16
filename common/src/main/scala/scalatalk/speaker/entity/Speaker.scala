package scalatalk.speaker.entity

case class Speaker(
										id: String,
										name: String,
										bio: Option[String] = None
									)
