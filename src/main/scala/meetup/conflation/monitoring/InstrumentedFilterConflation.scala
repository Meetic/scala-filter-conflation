package meetup.conflation.monitoring

import akka.actor.Actor
import kamon.Kamon
import meetup.conflation.monitoring.InstrumentedFilterConflation.log
import org.slf4j.{Logger, LoggerFactory}

object InstrumentedFilterConflation {

  val log: Logger = LoggerFactory.getLogger(classOf[InstrumentedFilterConflation])
}

trait InstrumentedFilterConflation {
  this: Actor =>

  val recorder = Kamon.metrics.entity(FilterConflationMetricsRecorder, self.path.toStringWithoutAddress)
  Kamon.metrics.shouldTrack(self.path.toStringWithoutAddress, FilterConflationMetricsRecorder.category)

  log.info(s"Instrumenting for ${self.path.toStringWithoutAddress}")
}
