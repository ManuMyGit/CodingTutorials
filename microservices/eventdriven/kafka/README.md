# Introduction
Before talking about Kafka, let's talk about how companies start.
 - At first it is super simple. There is a source system (a server), a target system (a client) and there is a data interchange between them (such as http requests).
 - After a while, the company keeps growing up and we find that we have lots of source systems and lots of target systems. **Let's imagine something like Uber: there are a lot of sources of information (thousand of cars) and at least a few systems (mobile applications, cars monitoring, reporting, ...).**

The problem is that with common architecture, if for instance we have 3 source systems and 6 target systems, we need to code 18 integrations! And each integration comes with difficulties: protocol (TCP, HTTP, REST, ...), data format (Json, XML, ...), data schema & how the data evolves, ...

How to manage it? Here is where Apache Kafka comes in. It allows us to decouple data streams and our systems, so our source systems will have the data ended up in Apache Kafka, and our target systems will get the data from it. For instance, what do we have in Kafka? Any data stream, like web site events, pricing data, financial transactions, user interactions, ... Additionally, once in Apache Kafka, we may want to put it in any system we want: database, analytics system, ...

## Quick review of Kafka features and use cases
Main features:
- Distributed.
- Resilient architecture.
- Fault tolerant.
- Horizontal scalability.
  - Can scale to 100s of brokers.
  - Can scale to millions of messages per second.
- High performance (latency of less than 10ms) - real time.

Real use cases:
- Messaging system.
- Activity tracking.
- Gather metrics from many different locations.
- Application logs gathering.
- Stream processing.
- De-coupling of system dependencies.

# Kafka fundamentals
## Topics, partitions and offsets
A topic in Kafka is the base of everything. It is a particular stream of data:
- We could say it is similar to a table in a database, in the way that all the data within the topic is of the same type.
- We can have as many topics as we want.
- Topics are split into partitions.
  - Each partition is ordered. The order is guaranteed just within each specific partition, not across partition.
  - Each message within a partition gets an incremental id, called offset.
  - The partitions must be specified when a topic is created. It can be changed later on, but it is a required data during the topic creation.
  - Offsets are duplicated across partitions, but not inside partitions. Offsets are unique per partition.
  - The offset itself doesn't say anything about the message. A message is identified by topic-partition-offset.
- The data is kept only for a limited time (default is one week).
- Once the data is written to a partition, it can't be changed (immutability).
- Data is assigned randomly to a partition unless a key is provided.

Here is a representation of topic, partitions and offsets:

![](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/images/image3.png)

Let's see an example. Let's thing of Uber:
- Uber has lots of cars running at anytime, and the company needs to know the position of every single car.
- Each car reports its GPS position to Kafka, let's say every 5 seconds.
- We could have then a topic called `"cars_gps_position"`. This topic will have the position of all cars.
- Each 5 seconds, each car will send a message to that topic which will contain the car ID and its position.
- We choose to create that topic with 50 partitions.
- Thanks to Kafka, Uber will able to show the position of the cars in the mobile app, it'll be able to send notifications when the cars get close to the clients, ...

## Brokers and topics
The brokers are the things which hold the topics/partitions. A Kafka cluster is composed of multiple brokers (servers):
- Each broker is identified with its ID, which must be a number.
- Each broker contains only certain topic of partitions. That means that a partition is hold just by one broker.
- After connecting to any broker (called a bootstrap broker), we'll be connected to the entire cluster.
- A good number to get started is 3 brokers:
  - That way, if one broker needs maintenance and one broker breaks down, there is still one broker working so the topic is still accessible by the consumers.

Let's say that we have 3 brokers and a topic called `"my-topic"` with 3 partitions:
- The first partition of the topic (my-topic Partition 2) is going to be in the broker 1.
- The second partition of the topic (my-topic Partition 1) is gonna be in the broker 2.
- The third partition of the topic (my-topic Partition 0) is gonna be in the broker 3.

As we can see, there is no relationship between brokers and partitions. What if we have a topic with less partitions than brokers? Well, there will be brokers with no data of that topic. What if we have a topic with more partitions than brokers? Well, there will be at least 1 broker with more than 1 partition of that topic. Here we can see the 3 scenarios:

![](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/images/image2.png)

## Topic replication
As we've seen, Kafka is a distributed system. And when there is a distributed system, we need replication so if a machine goes down, the data is still available, the data can be served by another server.

Topics should have a replication factor > 1 (usually between 2 and 3). The number of replicas = number configured - 1. That way, factor replica = 1 means there is no replica, only the main partition.

As any other distributed system, at any time only one broker can be a leader for a given partition (in the same way that only one node can be the leader of a database cluster for instance). Only the leader will be receiving and serving data for a partition, the brokers will just sync the data. Therefore, each partition has one leader and multiple in-sync replica.

In the following example, we show 3 brokers with 1 topic with 2 partitions and factor replica = 2 (leader partition in blue, replica in red):

![](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/images/image1.png)

What happens if we lose Broker 1? Well, Broker 0 will keep serving Partition 0 as (the replica got lost) and Broker 2 will be serving Partition 1 thanks to the replica.

## Producers and message keys
How do we get data in Kafka? That's the task of the producers.
- Producers write data to topics (which is made of partitions).
- Producers automatically know to which broker and partition to write to. They are kind of magical, because they automatically know it.
- In case of Broker failure, Producers will automatically recover.

Data is sent from the producers to the brokers round-robin unless a key is specified. Basically, by sending data to Kafka, to a topic, the producer will load balance.

Producers can choose to receive ack of data writes. There are 3 type of acks:
- `acks = 0`: producer won't wait for ack. That can produce a data loss.
- `acks = 1`: producer will wait for leader ack. That can produce a limited data loss, because if the leader breaks down, nothing guarantees replicas has the data.
- `acks = all`: producer will wait for leader + replicas ack. No data loss.

Producers wan choose to send a key with the message:
- The key can be a string, number, ...
- If key = null, round robin strategy is used.
- If key is sent, then all messages for that key will always go to the same partition. For instance, in our Uber example, where there is data every 5 seconds, if we want the data of a particular car to go always to the same partition, we can send the id of the car to guarantee all the data of the same car will go to the same partition.
- This mechanism of keying our partitions is called key hashing. We don't say "this key go to this partition", the only thing Kafka guarantees is "this key will go always to the same partition", but we don't know that partition.

## Consumers & consumer groups
In the same way producers write data, consumers read data from a topic (specified by name).
- Consumers know which broker to read from automatically, that's part of Kafka. We don't need to worry about it. They will use automatically a GroupCoordinator and a ConsumerCoordinator to assign a consumer to a partition.
- In case of broker failures, consumers know how to recover. Thanks to the partition-offset, if a broker is down, when it gets back, the consumer will keep reading from the last partition-offset.
- Data is read in order within each partition.
- In case of a consumer reading from more than one partition, partition reads will happen in parallel (but as we know, reads inside the partition will happen offset by offset).

Consumers can group to build consumer groups:
- Each consumer within a group reads from exclusive partitions.
- If we have more consumers than partitions, some consumers will be inactive. This scenario could be built on purpose, for instance 3 partitions and 4 consumers in case a consumer breaks down, the 4th one takes place to keep consuming with 3 consumers.
- If we have more partitions than consumers, some consumers will read from more than one partition.

In the following example we have 1 topic with 3 partitions read by 3 different consumer groups:

![](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/images/image4.png)

## Consumer offsets & delivery semantics
- Kafka stores the offsets at which a consumer group has been reading.
- The offsets committed live in a Kafka topic named `__consumer_offsets`.
- When a consumer in a group has processed data received from Kafka, it should be committing the offsets. This can be done automatically for us or can be programmed.
- That is needed because if a consumer dies, it'll be able to read back from there it left off thanks to the committed consumer offsets.

The offsets commitments implies something called delivery semantics. Consumers choose when to commit offsets. There are 3 types:
- At most once: 
  - Offsets are committed as soon as the message is received.
  - If the processing goes wrong, the message will be lost.
  - This mode is not desirable.
- At least once (usually preferred):
  - Offsets are committed after the message is processed.
  - If the processing goes wrong, the message will be read again.
  - This can result in duplicate processing of message. Make sure the processing is idempotent (processing again the message won't impact the systems).
- Exactly once:
  - Can be achieve for Kafka to Kafka workflows using Kafka Streams API.
  - For Kafka to External System workflows, use at least once with an idempotent consumer.

## Kafka broker discovery
As we said, the producers and consumers can automatically figure out which broker they can send data to-receive data from and so and so on. How does it work?
- Every Kafka broker is called bootstrap server.
- That means that we only need to connect to one broker and we'll be connected to the entire cluster.
- Each brokers knows about all brokers, topics and partitions (metadata).
- When a Kafka client (producer or consumer) connects to a broker of the cluster (any), the client behind the scenes will do a metadata request to get a list of all brokers, the ips, ... Once it starts to produce/consume it knows to which broker it needs to connect automatically. 

This is already implemented in Kafka, there is no need to worry about it.

## Zookeeper
Zookeeper is what holds the brokers together, it manages the brokers, it keeps a list of them:
- It helps in performing leader election for partitions.
- It sends notifications to Kafka in case of changes (new topic, broker dies, broker comes up, delete topics, ...).
- Kafka can't work without Zookeper.
- It by design operates with an odd number of servers (3, 5, 7, ...).
- As any other distributed system, there is a leader, which is the one which handle the writes. The rest of the servers are followers, and they handle reads.
- It doesn't store consumer offsets with Kafka > v0.10.

## Kafka guarantees
It is important, after understanding how Kafka works, what it guarantees:
- Messages are appended to a topic-partition in the order they are sent.
- Consumers read messages in the order stored in a topic-partition.
- With a replication factor of N, producers and consumers can tolerate up to a N-1 brokers being down. This is why a replication factor of 3 is a good idea:
  - Allows for one broker to be taken down for maintenance.
  - Allows for another broker to be taken down unexpectedly.
- As long as the number of partitions remain constant for a topic, the same key will always go to the same partition.

## Client Bi-Directional compatibility
As of Kafka 0.10.2, clients and Kakfa brokers have a capacity called bi-directional compatibility. This means:
- An older client (ex 1.1) can talk to a newer broker (ex 2.0).
- A newer client (ex 2.0) can talk to an older broker (ex 1.1).

That means that we should use always the latest client library as long as we can.

## Summarize
Let's see the following picture to summarize everything we know so far:

![](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/images/image5.png)

# Kafka CLI
## Kafka topics
- Command: `kafka-topics`
- Creation example: `kafka-topics --bootstrap-server localhost:9092 --topic my_topic --create --partitions 3 --replication-factor 1`
  - We need to tell the command where the Kafka server is (in this case, it is running locally, port 9092).
  - Number of partitions: 3
  - Number of replicas: 2. We'll get an error provided the number of replicas is bigger than the number of brokers. There can be more brokers than replicas, but not more replicas than brokers.
- List all topics: `kafka-topics --bootstrap-server localhost:9092 --list`
- Get the details of a topic: `kafka-topics --bootstrap-server localhost:9092 --topic my_topic --describe`
- Delete a topic: `kafka-topics --bootstrap-server localhost:9092 --topic my_topic --delete` 

## Kafka producer
- Command: `kafka-console-producer`
- Write data: `kafka-console-producer --broker-list 127.0.0.1:9092 --topic topic_name`
  - If the topic typed in the command doesn't exist, we'll get a WARN after running the command, but the topic will be created. The warning is due to the fact of not having a broker leader for the partition. When the command is run, the topic is created, but not fast enough to get it configured. But, as we said, the producers have a recovery mechanism, so the data can be written eventually. The default configuration for the new topic created is partition = 1 and replication factor = 1.
  - The broker-list parameter is a list of server:port brokers, so in case of writing data to more than one brokers, we need to type them here.
  - If everything goes well, a prompt will be showed to start streaming the data.
  - If we want to write data with key, the options `--property parse.key=true  --property key.separator=,` must be used. Example: `kafka-console-producer --broker-list localhost:9092  --topic first_topic --property parse.key=true  --property key.separator=,`.
  - Properties can be set to this command by using the `--producer-property` option. For instance:
    - ACKs: `--producer-property acks=all`

## Kafka consumer
- Command: `kafka-console-consumer`
- Read data: `kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic topic_name`
  - The bootstrap-server parameter is a list of server:port brokers, so in case of reading data from more than one brokers, we need to type them here.
  - If we write some data, and after writing the data we run this command, nothing will be printed. This is because it only reads from the point when it is launched. From then, it'll read the new data. This makes sense because we're streaming data, we want to read them in real time.
  - However, if we want to read the data from the beginning, we can use the option `--from-beginning` to read even the ones previously read.
  - If we want to read data with a key, we need to use the properties `--property print.key=true --property key.separator=,`. Example: `kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,`.
- Read data as part of consumer group: `kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic topic_name --group group_name`
  - If we run this command several times (to create several consumers part of the same group), when we send data to this topic, we'll see that the data is not displayed in all consumers at the same time, but the data is read across all the consumers within the group (round robin).
  - It is important to remember the offset commitment. When a message is read, the offset is committed (depending on the strategy selected), so if the consumer runs again, it won't read all the messages from the very beginning, but from the point it got shut down.

## Kafka consumer group
- Command: `kafka-consumer-groups`
- This command is used to manage the consumer groups: list consumer groups, describe a consumer group, delete consumer group info or reset consumer group offsets.
- List all consumer groups: `kafka-consumer-groups --bootstrap-server localhost:9092 --list`.
  - When the console consumer command is used with no consumer group, a new randomly named consumer group is created. So --list is used, we'll be able to see those random consumer groups.
- Describe a consumer group: `kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group consumer_group_name`.
- Reset consumer group offsets: `kafka-consumer-groups --bootstrap-server localhost:9092 --topic topic_name --group consumer_group_name --reset-offsets option --execute`. We can reset the offsets according to different options:
  - `--to-datetime <String: datetime>`: start at the datetime specified. Format must be YYYY-MM-DDTHH:mm:SS.sss
  - `--by-period <String: duration>`: reset offsets to offset by duration from current timestamp. Format: 'PnDTnHnMnS'.
  - `--to-earliest`: start at the beginning again. Example: `kafka-consumer-groups --bootstrap-server localhost:9092 --topic my_topic --group consumer_group_name --reset-offsets --to-earliest --execute`.
  - `--to-latest`: reset offsets to the latest offset.
  - `--shift-by <Long n>`: reset offsets shifting current offset by 'n', where 'n' can be positive or negative.
  - `--from-file <String: path to CSV file>`:  Reset offsets to values defined in CSV file.
  - `--to-current`: reset offsets to current offset.

## Kafka configuration
To fully configure producers and consumers we have the Kafka documentation:
- [Producers](https://kafka.apache.org/documentation/#producerconfigs)
- [Consumers](https://kafka.apache.org/documentation/#consumerconfigs)

## About the example
The example linked to this tutorial consists of 3 modules and 1 folder:
1. [Local](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/local): this folder contains a docker-compose file to be able to run a Kafka & Zookeeper server locally without worrying about the installation. To run the server, just type `docker-compose up` and the Kafka server will be running in the port 9092 and Zookeeper in the 2181.  
2. [Producer](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/producer): this project exposes an API in the port 8080 to write messages to Kafka.
   - "/publishkt": the message will be sent by using the kafka template. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishkt?topic=my_topic` and the body `{"message":"myMessage"}`.
   - "/publishkp": the message will be sent by using the kafka producer. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishkp?topic=my_topic` and the body `{"message":"myMessage"}`.
3. [Consumer](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/consumer): this project exposes 3 consumers to read from Kafka.
   - The first one is called MyKafkaConsumer. This class implements the interface AcknowledgingMessageListener and is linked to the messageListenerContainer for a specific topic.
   - The second one is called KafkaConsumers. We use the Spring annotation @KafkaListener to create another consumer.
   - The third one is called ConsumerServiceImpl. The consumer is create here by using the KafkaConsumer class. A fixed thread pool is used to run this task concurrently.
