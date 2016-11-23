package meetup.conflation.conflation

import scala.collection.mutable
import scala.concurrent.duration._

trait FilterConflation[E] {

  // The delay during which messages will be conflated
  val delay = 60 seconds

  // The interval at which the buffer will be checked for expired entries
  val tickInterval = 10 seconds

  // The buffer will hold the current conflation window for each providerId :
  // userId (String) is the key and time (Long) of the first event received is the value
  def buffer: mutable.HashMap[String, Long]

  // Method to check if the event's userId is currently filtered and send it otherwise
  def sendOrFilterEvent(event: E): Unit

  // Method to purge the buffer of expired entries
  def purgeFilteredEvents(): Unit
}