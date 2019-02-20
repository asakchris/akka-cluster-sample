package sample.cluster;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.management.javadsl.AkkaManagement;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class EventMain {
    public static void main(String[] args) {
        /*String port = args[0];
        System.out.println("port: " + port);*/

        /*Config config =
                ConfigFactory.parseString(
                        //"akka.remote.netty.tcp.port="
                        "akka.remote.artery.canonical.port="
                                + port)
                        //.withFallback(ConfigFactory.parseString("akka.cluster.roles = [compute]"))
                        .withFallback(ConfigFactory.load("event"));*/

        Config config = ConfigFactory.load("event");

        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(system);//.withRole("parent");

        system.actorOf(EventDetailWorkerCreator.props(), "event-detail-workers");
        system.actorOf(ClusterSingletonManager.props(EventDetailActor.props(), PoisonPill.getInstance(), settings), "EventDetailActor");

        AkkaManagement.get(system).start();
    }
}
