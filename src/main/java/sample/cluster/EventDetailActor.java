package sample.cluster;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.routing.FromConfig;

import java.time.Duration;
import java.util.UUID;

public class EventDetailActor extends AbstractLoggingActor {
    private ActorRef workerRouter = context().actorOf(FromConfig.getInstance().props(EventDetailWorkerActor.props()), "event-detail-router");
    private final Cluster cluster = Cluster.get(context().system());

    public EventDetailActor() {
        context().system().scheduler().schedule(Duration.ofSeconds(10), Duration.ofSeconds(60), self(), UUID.randomUUID().toString(), context().system().dispatcher(), self());
    }

    @Override
    public void preStart() {
        cluster.subscribe(self(), ClusterEvent.MemberUp.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, value -> {
                    log().info("EventDetailActor received message: {}", value);
                    workerRouter.tell(value, self());
                })
                .build();
    }

    public static Props props() {
        return Props.create(EventDetailActor.class, () -> new EventDetailActor());
    }
}
