# Pizza Factory : Conflation 'filter' example app

See the first part : https://github.com/Meetic/scala-merge-conflation

## The Pizza Web

This is an example application that processes Session events in real time, for a virtual Pizza Factory company's website.
Each event has an 'userId' to represent the concerned User.

The application uses the Akka framework, 3 actors are involved in the virtual process :
 - SourceActor : virtually is the source of events and actual randomly generates them for a range of 'userId's
 - ConflationActor : will implement the conflation logic
 - ProcessActor : virtually processes the events, and actually just log them
 
## Monitoring

Monitoring with Kamon is used to measure the efficiency of the Conflation in the ConflationActor.
A statsd + Graphite + Grafana stack can be used to visualize the data, a docker-compose is provided by Kamon at https://github.com/kamon-io/docker-grafana-graphite.
The following metrics are produced :
 - in (counter) : events received by the ConflationActor
 - out (counter) : events sent by the ConflationActor
 - filtered (counter) : events filtered (thus actually dropped) by the ConflationActor
 - buffer (histogram) : size of the conflation buffer, that contains an entry for each User, when it is checked for expired entries
 
## Branches

There are 2 steps to introduce the Conflation, and for each before and after steps to highlight the implementation in the ConflationActor.
 - master : Initial PizzaFactory application without conflation (the ConflationActor just fowards the events)
 - step-1-merge-conflation : We introduce an interface with the methods used to implement the Conflation
 - solution-1-merge-conflation : Implementation done in the ConflationActor
 - step-2-monitoring : We introduce Kamon and the Kamon recorder for our Conflation implementation
 - solution-2-monitoring : The measures are collected in the ConflationActor
