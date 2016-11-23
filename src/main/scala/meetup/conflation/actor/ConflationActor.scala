package meetup.conflation.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import meetup.conflation.actor.ConflationActor.Tick
import meetup.conflation.conflation.{Conflated, FilterConflation}
import meetup.conflation.event.SessionEvent

import scala.collection.mutable

object ConflationActor {

  case object Tick
  def props(target: ActorRef): Props = Props(new ConflationActor(target))
}

class ConflationActor(target: ActorRef) extends Actor with ActorLogging with FilterConflation[SessionEvent] {

  override val buffer = mutable.HashMap[String, Conflated[SessionEvent]]()

  override def sendOrFilterEvent(event: SessionEvent): Unit = {
    if (! buffer.contains(event.userId)) {
      target ! event
      buffer.put(event.userId, Conflated(event, System.currentTimeMillis()))
    }
    // else ignore event
  }

  override def purgeFilteredEvents(): Unit = {
    log.info(s"Purging filtered events (${buffer.size})")
    val threshold = System.currentTimeMillis() - delay.toMillis
    buffer.retain {
      case (userId, Conflated(_, time)) if time < threshold => false
      case _ => true
    }
  }

  override def receive: Receive = {
    case event: SessionEvent => sendOrFilterEvent(event)
    case Tick => purgeFilteredEvents()
  }

  import context.dispatcher
  context.system.scheduler.schedule(tickInterval, tickInterval, self, Tick)
}
