package meetup.conflation.monitoring

import kamon.metric.instrument.InstrumentFactory
import kamon.metric.{EntityRecorderFactory, GenericEntityRecorder}

class FilterConflationMetricsRecorder(instrumentFactory: InstrumentFactory) extends GenericEntityRecorder(instrumentFactory) {
  protected val in = counter("in")
  protected val out = counter("out")
  protected val filtered = counter("filtered")
  protected val buffer = histogram("buffer")

  def recordIn(): Unit = {
    in.increment()
  }
  def recordOut(): Unit = {
    out.increment()
  }
  def recordFiltered(): Unit = {
    filtered.increment()
  }
  def recordBufferSize(size: Int): Unit = {
    buffer.record(size)
  }

}

object FilterConflationMetricsRecorder extends EntityRecorderFactory[FilterConflationMetricsRecorder] {
  override def category: String = "filter-conflation"
  override def createRecorder(instrumentFactory: InstrumentFactory): FilterConflationMetricsRecorder =
    new FilterConflationMetricsRecorder(instrumentFactory)
}
