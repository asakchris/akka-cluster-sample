# Akka Cluster example
This project contains below:
* Cluster Singleton Actor
* Cluster Aware Routing Group
* Helath check End Points with Akka Management

Use below commands to run:

sbt '; set javaOptions += "-Dcluster.port=2551 -Dhttp.port=8551" ; runMain sample.cluster.EventMain'

sbt '; set javaOptions += "-Dcluster.port=2552 -Dhttp.port=8552" ; runMain sample.cluster.EventMain'

docker run -p 2551:2551 -p 8558:8558 -t asakchris/akka-cluster-sample:latest

-Dlogger-root-level=DEBUG -Dconfig.resource=/aws.conf
-Dlogger-root-level=DEBUG -Dconfig.resource=/aws.conf -DACTOR_SYSTEM_NAME=events-wf -DCLUSTER_PORT=2553 -DHTTP_PORT=8559

https://github.com/akka/akka-management/tree/master/integration-test/aws-api-ec2