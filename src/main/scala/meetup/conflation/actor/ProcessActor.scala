package meetup.conflation.actor

import akka.actor.{Actor, ActorLogging, Props}
import meetup.conflation.event.SessionEvent

object ProcessActor {

  def props(): Props = Props(new ProcessActor)
}

class ProcessActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case event: SessionEvent =>
      log.info(s"Processing $event")
  }
}
