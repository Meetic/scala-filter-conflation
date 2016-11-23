package meetup.conflation.conflation

import scala.collection.mutable
import scala.concurrent.duration._

trait FilterConflation[E] {

  val delay = 60 seconds
  val tickInterval = 10 seconds

  def buffer: mutable.HashMap[String, Conflated[E]]

  def sendOrFilterEvent(event: E): Unit

  def purgeFilteredEvents(): Unit
}

case class Conflated[E](event: E, time: Long)