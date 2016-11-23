package meetup.conflation.actor

import java.time.ZonedDateTime

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import meetup.conflation.event.SessionEvent

import scala.concurrent.duration.FiniteDuration
import scala.util.Random

class SourceActor(target: ActorRef, idRangeStart: Int, idRangeEnd: Int, delay: FiniteDuration) extends Actor with ActorLogging {

  log.info(s"Starting Source with ids in range $idRangeStart -> $idRangeEnd")

  val possibleIds = Range(idRangeStart, idRangeStart + idRangeEnd).toArray
  val possibleOrigins = List("web", "android", "ios", "windows-phone", "mobile")

  Iterator.continually().map { _ =>
    SessionEvent(
      possibleIds(Random.nextInt(idRangeEnd - idRangeStart)).toString,
      possibleOrigins(Random.nextInt(possibleOrigins.size)),
      ZonedDateTime.now()
    )
  }.foreach { event =>
    Thread.sleep(delay.toMillis)
    target ! event
  }

  override def receive: Receive = PartialFunction.empty

}

object SourceActor {

  def props(target: ActorRef, idRangeStart: Int, idRangeEnd: Int, delay: FiniteDuration): Props =
    Props(new SourceActor(target, idRangeStart, idRangeEnd, delay))
}