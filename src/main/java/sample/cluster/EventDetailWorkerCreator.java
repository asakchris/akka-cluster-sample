package sample.cluster;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

import java.util.stream.IntStream;

public class EventDetailWorkerCreator extends AbstractLoggingActor {
    @Override
    public void preStart() {
        IntStream.rangeClosed(1, 3).forEach(i -> context().actorOf(EventDetailWorkerActor.props(), "w" + i));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }

    public static Props props() {
        return Props.create(EventDetailWorkerCreator.class, () -> new EventDetailWorkerCreator());
    }
}
