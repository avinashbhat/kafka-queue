# Queuing using Kafka

Aim is to build a queuing system for accepting and parsing the log of IoT devices.

## Prerequisites

To run the project Kafka and ZooKeeper have to be installed. These can be downloaded from

* Kafka - https://kafka.apache.org/downloads
* ZooKeeper - http://zookeeper.apache.org/releases.html

### Installing

The steps required to install these, are available here.
* Kafka - https://www.tutorialspoint.com/apache_kafka/apache_kafka_installation_steps.htm
* ZooKeeper - https://www.tutorialspoint.com/zookeeper/zookeeper_installation.htm

## Deployment

Before we run the code, it is necessary to download the JSON library as I have used it to parse the Consumer Record. For convenience, I have included it here along with the code. Copy it to '/home/../kafka_2.11-0.9.0.0/libs'. Start the ZooKeeper Server, as given in the above mentioned reference. Next, create a topic 'iot', the steps are given in the above reference.
Next, on terminal navigate to the working directory and type

```
$javac -cp "/home/.../kafka_2.11-0.9.0.0/libs/*" NewProducer.java 
```
to compile the Producer,
```
javac -cp "/home/.../kafka_2.11-0.9.0.0/libs/*" NewConsumer.java
```
to compile the Consumer. Next, run the Consumer by typing 
```
java -cp "/home/.../kafka_2.11-0.9.0.0/libs/*":. NewConsumer iot
```
and on another terminal, type 
```
java -cp "/home/.../kafka_2.11-0.9.0.0/libs/*":. NewConsumer iot
```
to run the Producer. The Consumer terminal will show the required output.

## Authors

* **Avinash Bhat** - (https://github.com/avinashbhat)

## References

* tutorialspoint - (https://www.tutorialspoint.com/)
* StackOverflow
