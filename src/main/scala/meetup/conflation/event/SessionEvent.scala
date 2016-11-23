package meetup.conflation.event

import java.time.ZonedDateTime

case class SessionEvent
(
  userId: String,
  origin: String,
  time: ZonedDateTime
)
