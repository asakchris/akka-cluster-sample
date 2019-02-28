package sample.cluster;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.discovery.awsapi.ecs.AsyncEcsServiceDiscovery;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.util.Either;

import java.net.InetAddress;
import java.util.Properties;

public class EventMain {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        String envType = config.getString("app.envType");
        System.out.println("envType: " + envType);

        if("AWS".equals(envType)) {
            InetAddress privateAddress = getContainerAddress();
            String hostAddress = privateAddress.getHostAddress();
            System.out.println("hostAddress: " + hostAddress);
            Properties properties = new Properties();
            properties.setProperty("akka.management.http.hostname", hostAddress);
            //properties.setProperty("akka.remote.artery.canonical.hostname", hostAddress);
            properties.setProperty("akka.remote.netty.tcp.hostname", hostAddress);
            config = ConfigFactory.parseProperties(properties).withFallback(ConfigFactory.load());
        }

        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(system);//.withRole("parent");

        system.actorOf(EventDetailWorkerCreator.props(), "event-detail-workers");
        system.actorOf(ClusterSingletonManager.props(EventDetailActor.props(), PoisonPill.getInstance(), settings), "EventDetailActor");

        AkkaManagement.get(system).start();
        ClusterBootstrap.get(system).start();
    }

    private static InetAddress getContainerAddress() {
        final Either<String, InetAddress> address = AsyncEcsServiceDiscovery.getContainerAddress();
        if(address.isLeft()) {
            System.err.println("Unable to get container address, so exiting - " + address.left().get());
            System.exit(1);
        }
        return address.right().get();
    }
}
