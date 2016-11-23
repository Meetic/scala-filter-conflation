package meetup.conflation.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import meetup.conflation.actor.ConflationActor.Tick
import meetup.conflation.conflation.FilterConflation
import meetup.conflation.event.SessionEvent
import meetup.conflation.monitoring.InstrumentedFilterConflation

import scala.collection.mutable

object ConflationActor {

  case object Tick
  def props(target: ActorRef): Props = Props(new ConflationActor(target))
}

class ConflationActor(target: ActorRef) extends Actor with ActorLogging
  with FilterConflation[SessionEvent] with InstrumentedFilterConflation {

  override val buffer = mutable.HashMap[String, Long]()

  override def sendOrFilterEvent(event: SessionEvent): Unit = {
    recorder.recordIn()
    if (! buffer.contains(event.userId)) {
      target ! event
      recorder.recordOut()
      buffer.put(event.userId, System.currentTimeMillis())
    }
    else {
      // else ignore event
      recorder.recordFiltered()
    }
  }

  override def purgeFilteredEvents(): Unit = {
    log.info(s"Purging filtered events (${buffer.size})")
    recorder.recordBufferSize(buffer.size)

    val threshold = System.currentTimeMillis() - delay.toMillis
    buffer.retain {
      case (_, time) if time < threshold => false
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
