package meetup.conflation

import akka.actor.ActorSystem
import meetup.conflation.actor.{ConflationActor, ProcessActor, SourceActor}
import scala.concurrent.duration._

object PizzaWeb extends App {

  implicit val system = ActorSystem("pizza-web")

  val idRangeStart = 10000
  val idRangeEnd = 18000
  val sourceDelay = 2 millis

  // [ Source ] -> [ Conflation ] -> [ Process ]

  val processActor = system.actorOf(ProcessActor.props(), "Process")
  val conflationActor = system.actorOf(ConflationActor.props(processActor), "Conflate")
  system.actorOf(SourceActor.props(conflationActor, idRangeStart, idRangeEnd, sourceDelay), "Source")
}
