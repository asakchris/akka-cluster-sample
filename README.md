# Akka Cluster example
This project contains below:
* Cluster Singleton Actor
* Cluster Aware Routing Group
* Helath check End Points with Akka Management

Use below commands to run events-eod cluster in local:
```
sbt '; set javaOptions += "-Dconfig.resource=/local.conf" ; runMain sample.cluster.EventMain'
sbt '; set javaOptions += "-Dconfig.resource=/local.conf -DCLUSTER_PORT=2553 -DHTTP_PORT=8559" ; runMain sample.cluster.EventMain'
```

Use below commands to run events-wf cluster in local:
```
sbt '; set javaOptions += "-Dconfig.resource=/local.conf -DACTOR_SYSTEM_NAME=events-wf -DCLUSTER_PORT=2550 -DHTTP_PORT=8550" ; runMain sample.cluster.EventMain'
sbt '; set javaOptions += "-Dconfig.resource=/local.conf -DACTOR_SYSTEM_NAME=events-wf -DCLUSTER_PORT=2551 -DHTTP_PORT=8551" ; runMain sample.cluster.EventMain'
```

Use below command to build docker image and push it to docker hub repository:
```
sbt docker:publish
```

Use below command to run in docker container:
```
docker run -p 2552:2552 -p 8558:8558 –env JAVA_OPTS="-Dconfig.resource=/local.conf" -t asakchris/akka-cluster-sample:latest
```

AWS cloud formation template to create ECS cluster, Task Definition & Service available in cfn-templates/ecs-cluster.yaml. Use below command to validate template before running it:
```
aws cloudformation validate-template --template-body file://ecs-cluster.yaml
```

Run below command to create all required AWS resources:
```
aws cloudformation create-stack --stack-name akka-poc --template-body file://ecs-cluster.yaml --capabilities CAPABILITY_IAM --parameters ParameterKey=KeyName,ParameterValue=***** ParameterKey=VpcId,ParameterValue=vpc-***** ParameterKey=SubnetId,ParameterValue='subnet-******,subnet-******,subnet-*****'
```

Akka Cluster in AWS ECS Cluster using discovery method AWS API - ECS Discovery, refer [this](https://developer.lightbend.com/docs/akka-management/current/discovery/aws.html) for more details. Followed [this](https://github.com/akka/akka-management/tree/master/integration-test/aws-api-ecs) example.