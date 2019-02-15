package sample.cluster;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class EventDetailWorkerActor extends AbstractLoggingActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, value -> {
                    log().info("EventDetailWorkerActor received message: {}", value);
                })
                .build();
    }

    public static Props props() {
        return Props.create(EventDetailWorkerActor.class, () -> new EventDetailWorkerActor());
    }
}
