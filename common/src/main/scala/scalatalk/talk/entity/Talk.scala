package scalatalk.talk.entity

case class Talk(
								 id: String,
								 speaker: String,
								 event: String,
								 title: Option[String] = None,
								 desc: Option[String] = None,
								 video: Option[String] = None
							 )
