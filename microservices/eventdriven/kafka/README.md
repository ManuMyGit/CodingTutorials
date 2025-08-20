# Introduction
Before talking about Kafka, let's talk about how companies start.
 - At first it is super simple. There is a source system (a server), a target system (a client) and there is a data interchange between them (such as http requests).
 - After a while, the company keeps growing up and we find that we have lots of source systems and lots of target systems. **Let's imagine something like Uber: there are a lot of sources of information (thousand of cars) and at least a few systems (mobile applications, cars monitoring, reporting, ...).**

The problem is that with common architecture, if for instance we have 3 source systems and 6 target systems, we need to code 18 integrations! And each integration comes with difficulties: protocol (TCP, HTTP, REST, ...), data format (Json, XML, ...), data schema & how the data evolves, ...

How to manage it? Here is where Apache Kafka comes in. It allows us to decouple data streams and our systems, so our source systems will have the data ended up in Apache Kafka, and our target systems will get the data from it. For instance, what do we have in Kafka? Any data stream, like web site events, pricing data, financial transactions, user interactions, ... Additionally, once in Apache Kafka, we may want to put it in any system we want: database, analytics system, ...

## Quick review of Kafka features and use cases
Main features:
- Distributed: it's designed to run across multiple servers (brokers) that work together as a single unit, allowing for scalability, fault tolerance, and high availability.
- Resilient architecture: distributed, replicated, and fault-tolerant design, ensuring high availability and data durability even in the face of failures
- Fault tolerant: Kafka ensures data durability through its replication mechanism. Each partition is replicated across multiple brokers, with one broker acting as the leader and others as followers. When a producer sends a message, it is written to the leader and then replicated to the followers. This ensures that even if a broker fails, the data remains available.
- Horizontal scalability.
  - More partitions can be added and distributed across a cluster of brokers to handle increasing loads, and each partition can be replicated across multiple brokers to enhance fault tolerance.
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
## Message
A Kafka message, also known as a record, primarily consists of a key, a value, and a timestamp. The key is optional and used to determine the partition where the message will be stored. The value contains the actual data payload, and the timestamp indicates when the message was produced

## Topics, partitions and offsets
A topic in Kafka is the base of everything. It is a particular stream of data:
- We could say it is similar to a table in a database, in the way that all the data within the topic is of the same type.
- We can have as many topics as we want.
- Topics are split into partitions.
  - A topic is a logical representation of a stream of data. A partition represents the physical data storage on disk.
  - Each partition is ordered. The order is guaranteed just within each specific partition, not across partitions.
  - If order is required, all events related to the same object should be sent with the same key. This will guarantee Kafka will store the event in the same partition.
  - Messages across partitions within the same topic are read in parallel, while messages within the same partition are read sequentially.
  - Each message within a partition gets an incremental id, called offset.
  - The partitions must be specified when a topic is created. It can be changed later on, but it is a required data during the topic creation. Increase the number of partitions might be risky since it might change the partition destination for a set of keys. Imagine we have partitions 1, 2 and 3. All the messages with key ABCD go to partition 1, which guarantees order for that key. Assume now that we add a new partition, 4, and due to this, all messages with key ABCD go now to partition 4. If there are events for ABCD to be processed in partition number 1, and we have now events for that key in partition number 4, order is broken.
  - Offsets are duplicated across partitions, but not inside partitions. Offsets are unique per partition.
  - The offset itself doesn't say anything about the message. A message is identified by topic-partition-offset.
- The data is kept only for a limited time (default is one week).
- Once the data is written to a partition, it can't be changed (immutability).
- Data is assigned randomly to a partition unless a key is provided.

Here is a representation of topic, partitions and offsets:

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/eventdriven/kafka/images/image3.png)

Let's see an example. Let's thing of Uber:
- Uber has lots of cars running at anytime, and the company needs to know the position of every single car.
- Each car reports its GPS position to Kafka, let's say every 5 seconds.
- We could have then a topic called `"cars_gps_position"`. This topic will have the position of all cars.
- Each 5 seconds, each car will send a message to that topic which will contain the car ID and its position.
- We choose to create that topic with 50 partitions.
- Thanks to Kafka, Uber will able to show the position of the cars in the mobile app, it'll be able to send notifications when the cars get close to the clients, ...

### Partitions
To support scalability is fundamental to select a right  to distribute the messages accross different partitions. The partition is determined by hashing the key using a hash function (murmur2 by default) and taking the modulo with the number of partitions: partition = hash(key) % num_partitions. If you choose a bad key, you can end up with hot partitions that are overwhelmed with traffic. Good keys are ones that are evenly distributed across the partition space.

There are partitions which receive extremely high traffic under some conditions. These partitions are called hot partitions. How can we handle hot partitions? There are a few strategies to handle hot partitions:
- Random partitioning with no key (when order is not important): If you don't provide a key, Kafka will randomly assign a partition to the message, guaranteeing even distribution. The downside is that you lose the ability to guarantee order of messages. If this is not important to your design, then this is a good option.
- Random salting: We can add a random number or timestamp to the ID when generating the partition key. This can help in distributing the load more evenly across multiple partitions, though it may complicate aggregation logic later on the consumer side. This is often referred to as "salting" the key.
- Use a compound key: Instead of using just a simple ID, use a combination of ID and another attribute, such as geographical region or user ID segments, to form a compound key. This approach helps in distributing traffic more evenly and is particularly useful if you can identify attributes that vary independently of the ID.
- Back pressure: Depending on your requirements, one easy solution is to just slow down the producer. If you're using a managed Kafka service, they may have built-in mechanisms to handle this. If you're running your own Kafka cluster, you can implement back pressure by having the producer check the lag on the partition and slow down if it's too high.

## Brokers and topics
The brokers are the machines (servers) which hold the topics/partitions. A Kafka cluster is composed of multiple brokers (servers):
- Each broker is identified with its ID, which must be a number.
- Each broker contains only certain topic of partitions. That means that a partition is hold just by one broker.
- After connecting to any broker (called a bootstrap broker), we'll be connected to the entire cluster.
- A good number to get started is 3 brokers:
  - That way, if one broker needs maintenance and one broker breaks down, there is still one broker working so the topic is still accessible by the consumers.
- As a rule of thumb, a broker can store up to 1TB of data and can manage up to 10k messages per second.

Let's say that we have 3 brokers and a topic called `"my-topic"` with 3 partitions:
- The first partition of the topic (my-topic Partition 2) is going to be in the broker 1.
- The second partition of the topic (my-topic Partition 1) is gonna be in the broker 2.
- The third partition of the topic (my-topic Partition 0) is gonna be in the broker 3.

As we can see, there is no relationship between brokers and partitions. What if we have a topic with less partitions than brokers? Well, there will be brokers with no data of that topic. What if we have a topic with more partitions than brokers? Well, there will be at least 1 broker with more than 1 partition of that topic. Here we can see the 3 scenarios:

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/eventdriven/kafka/images/image2.png)

## Topic replication
As we've seen, Kafka is a distributed system. And when there is a distributed system, we need replication so if a machine goes down, the data is still available, the data can be served by another server.

Topics should have a replication factor > 1 (usually between 2 and 3). The number of replicas = number configured - 1. That way, factor replica = 1 means there is no replica, only the main partition.

As any other distributed system, at any time only one broker can be a leader for a given partition (in the same way that only one node can be the leader of a database cluster for instance). Only the leader will be receiving and serving data for a partition, the brokers will just sync the data. Therefore, each partition has one leader and multiple in-sync replica.

In the following example, we show 3 brokers with 1 topic with 2 partitions and factor replica = 2 (leader partition in blue, replica in red):

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/eventdriven/kafka/images/image1.png)

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

The key is used by Kafka to calculate the partition that will store the message. The partition is calculated by the following formula: `partition = hash(key) % number_of_partitions`. We want all partitions to receive roughly the same load. That means they key distribution matters a lot:
- Even distribution happens when:
  - Keys are diverse and fairly random (e.g., UUIDs, order IDs spread uniformly, session IDs, random hashes).
  - Or no key is used at all → round-robin ensures balance (but then ordering is lost).
- Uneven distribution happens when:
  - Keys have skewed frequencies (e.g., 90% of messages have key="user123" → one partition gets overloaded).
  - The number of distinct keys is too small compared to the number of partitions.

Chosing the right key depends on the business logic:
- If ordering per entity is needed, use that entity as the key (e.g., userId, accountId, orderId).
- If balanced throughput is the goal and don’t care about ordering, leave the key null → Kafka will round-robin.
- If ordering + even spread is required: pick a high-cardinality field (e.g., customerId if you have millions of customers, not countryCode if you only have 10). A field is high cardinality if it has many unique values compared to the number of partitions. Example: countryCode → only ~200 possible values (low cardinality). userId → millions of unique values (high cardinality).

To keep partitions balanced:
- Number of unique keys ≫ number of partitions (at least 10x more unique keys than partitions is a good practice).
- Check key frequency distribution → ideally uniform.

## Consumers & consumer groups
In the same way producers write data, consumers read data from a topic (specified by name).
- Consumers know which broker to read from automatically, that's part of Kafka. We don't need to worry about it. They will use automatically a GroupCoordinator and a ConsumerCoordinator to assign a consumer to a partition.
- In case of broker failures, consumers know how to recover. Thanks to the partition-offset, if a broker is down, when it gets back, the consumer will keep reading from the last partition-offset. Consumers commit the offset to the broker regularly, so when the consumer is back, kafka has the information about the offset.
- Data is read in order within each partition.
- In case of a consumer reading from more than one partition, partition reads will happen in parallel (but as we know, reads inside the partition will happen offset by offset).

Consumers can group to build consumer groups:
- Each consumer within a group reads from exclusive partitions.
- Kafka guarantees that a message is read only by one consumer within the consumer group.
- If we have more consumers than partitions, some consumers will be inactive. This scenario could be built on purpose, for instance 3 partitions and 4 consumers in case a consumer breaks down, the 4th one takes place to keep consuming with 3 consumers.
- If we have more partitions than consumers, some consumers will read from more than one partition.
- If we want to achieve a queue-like behaviour, we can assign all the consumers within the same consumer group. If we want the opposite, a broadcast-like behavoiur, we can create a consumer group per consumer.

In the following example we have 1 topic with 3 partitions read by 3 different consumer groups:

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/eventdriven/kafka/images/image4.png)

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

## KRaft
Zookeeper is being deprecated in favour of Kraft.

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

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/eventdriven/kafka/images/image5.png)

# Kakfa advanced
## Kafka Connect
Kafka connect is all about code & connection re-use. We're not the ones who want to try to get data from a specific source (like Twitter). We're not the ones who want to store the data in some database (like Mongo, or ElasticSearch), and we can guarantee that we're not the ones to include some bugs in our code.

What if we could use something that is already built and working? Well, this is Kafka connect.

Why do we even need Kafka Connect API? When we look at Kafka, there are 3 common cases:
- Source -> Kafka: Producer API (Kafka Connect Source).
- Kafka -> Kafka: Consumer, Producer API (Kafka Streams).
- Kafka -> Sink: Consumer API (Kafka Connect Sink).

So the goals of Kafka connect are:
- Simplify and improve getting data in and out of Kafka.
- Simplify transforming data within Kafka without relying on external libs.

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/eventdriven/kafka/images/image6.png)

## Kakfa Streams
Kafka streams are used to manipulate the data in some way (data transformation, data enrichment, fraud detection, monitoring and alerting, ...), so the source and the sink is the own broker. Basically, kafka streams are easy data processing and transformation library within kafka:
- Standard Java application.
- No need to create a separate cluster.
- Highly scalable, elastic and fault tolerant.
- Exactly once capabilities.
- One record at a time processing (no batches).

## Kafka schema registry
Kafka takes bytes as an input and publishes them, but there is no data verification in Kafka. So, what if producer sends bad data, or a field gets renamed, or the data format changes? That's why we need a schema registry:
- We need data to be self describable.
- We need to be able to evolve data without breaking downstream consumers.

What if Kafka brokers were verifying the messages upon receiving them? Well, it would break what makes Kafka so good:
- Kafka doesn't parse or even read our data (no CPU ussage).
- Kafka takes bytes as an input without loading them into memory.
- Kafka distributed bytes.
- As far as Kafka is connected, it doesn't even know if our data is a integer, string, ...

The schema registry:
- Has to be a separate component.
- Producers and Consumers need to be able to talk to it.
- Must be able to reject bad data.
- A common data format must be agreed upon (support schemas, evolution and be lightweight).

## Partitions count and replicate factor
These parameters are the most important ones when creating a topic. They impact performance and durability of the system overall.
- Modify the number of partitions breaks the key ordering guarantees, it is best to get the parameters right the first time.
- If the replication factor increases during a topic lifecycle, we put more pressure on our cluster, which can lead to unexpected performance decrease.

For partition counts:
- Each partition can handle a throughput of a few MB/s (it must be measure by ourselves since it depends on the hardware, network, ...).
- More partitions implies:
  - Better parallelism, better throughput.
  - Ability to run more consumers in a group to scale. If we have 3 partitions for instance, we are limited to 3 consumers in a group. If we expect high throughput, we will need a large number of partitions.
  - Ability to leverage more brokers if we have a large cluster. The more partitions we have, the more brokers we will use. If we have for instance 20 brokers and we create 2 partitions, only 2 brokers will be working on this topic.
  - But, the more partitions we have, the more elections to perform for Zookeeper, and more files will get opened in Kafka.

The guidelines for partition counts are:
- Small cluster (< 6 brokers): partitions = 2 x number of brokers.
- Medium (>= 6 brokers and <= 12 brokers): depending on the situation, but a good approximation of partitions is 12.
- Big cluster (> 12 brokers): partitions = number of brokers
- Adjust for number of consumers we need to run in parallel at peak throughput: if our consumers are gonna be very busy, very CPU intense, and we need for instance 20 consumers at peak time, then we'll need 20 partitions regardless the size of the broker.
- Adjust for producer throughput: increase if super-high throughput or projected increase in the next 2 years. For instance, 3 types instead of 2.
- Every Kafka cluster will have different performance, so test is very important.

For replication factor:
- Should be at least 2, usually 3, maximum 4.
- The higher the replication factor (N):
  - Better resilience of our system (N - 1 brokers can fail).
  - But more replication implies higher latency if acks = all and more disk space of our system..

The guidelines for replication factor are:
- Set it to 3 to get started (it means to have at least 3 brokers for that).
- If replication performance is an issue, get a getter broker instead of less replication factor.
- Never set it to 1 in production.

General guidelines:
- The maximum size of a message should be 1MB.
- It is pretty much accepted that a broker should not hold more than 2000 to 4000 partitions (across all topics of that broker).
- Additionally, a Kafka cluster should have a maximum of 20000 partitions across all brokers.
- The reason is that in case of brokers going down, Zookeeper needs to perform a lot of leader elections.
- If we need more partitions in our cluster, add brokers instead.
- If we need more than 20000 partitions in our cluster, follow the Netflix model and create more Kafka clusters.
- Overall, we don't need a topic with 1000 partitions to achieve high throughput. Start at a reasonable number and test the performance.

## Partitions, segments and indexes
- As we know, topics are made of partitions. But what are partitions made of? They are made of segments.
- Segments are literally files.
- Each partition consists of several segments. Each segment takes care of a certain group of offsets.
- The last segment is called the active one, because data is writen in that segment. Once the segment is full, it is closed, a new one is created and that segment becomes the active one. Only one segment is active at a time.

There are two settings linked to segments:
- log.segment.bytes: the maximum size of a single segment in bytes.
  - By default 1GB.
- log.segment.ms: the time Kafka will wait before committing the segment if not full.
  - By default 1 week.

Segments come with two indexes (files):
- An offset to position index: it allowes Kafka where to read to find a message.
- A timestamp to offset index: it allows Kafka to find a message with a timestamp.

Therefore, Kafka knows where to find data in a constant time.

Why should we care about segments?
- A smaller log.segment.bytes means more segments per partition. That means that log Compaction happens more often and Kafka needs to keep more files opened (which could lead to errors).
- A smaller log.segments.ms more frecuency for log compaction (more frequent triggers) and we'll get more files.

## Log cleanup policies
Many kafka clusters make data expire according to a policy. That is called log cleanup. To configure the policy we have the `log.cleanup.policy`.
- Kafka default for all user topics: delete.
  - Delete base on age data, by default 1 week.
  - Delete based in max size, by default -1 = infinite.
- Kafka defualt for topic __consumer_offsets: compact.
  - Delete based on keys of our messages.
  - It'll delete old duplicate keys after the active segment is committed.

Delete data from Kafka allowes us:
- Control the size of data on the disk, deleting obsolete data.
- Overall, limit maintenance work on the Kafka Cluster.

Log clueanup happens on our partition segments, so the smaller the segment size, the more segments will be created, and the more segments, the more log cleanup will happen more often. The gotcha is that log cleanup shouldn't happen very often because it takes CPU and RAM resources. Just out of curiosity, the cleaner checks job runs every 15 seconds (`log.cleaner.backoff.ms`), to check whether a log cleanup is needed or not.

### Log cleanup policy: delete
- `log.retention.hours`: number of hours to keep data for.
  - By default 168 (1 week).
  - Higher number means more disk space (the information is delete less often).
  - Lower number means that less data is reatined (if the consumers are down for too long, they can miss data).
- `log.retention.bytes`: max size in bytes for each partition.
  - By default -1 = infinite.
  - Useful to keep a log under a threshold.

Basically, old segment (old data) will be deleted based on time or space rules. New data is written to the active segment.

There are two common strategies:
- One week of retention (no matter the size of the data). This is the default configuration.
- Infinite time of retention bounded by a specific limit, for instance, 500MB. That way, the information won't be deleted if there is no need to because we have enough space:
  - `log.retention.hours=18000` and `log.retention.bytes=524288000` (500MB).

### Log cleanup policy: compact
Log compaction ensures that our logs contain at least the last known value for a specific key within a partition.
- It is very useful if we just require a SNAPSHOT instead of full history (such as for data table in a database).
- The idea is that we only keep the last update for a key in our log.

Let's say that in segment 0 we have salaries for some people. Then, we get newest salaries for those people in segment 1. After compaction, the people in segment 0 whose salary has been inserted in segment 1 will be deleted.

Log compaction guarantees:
- Any consumer that is reading from the tail of a log (most current data) will still see all the messages sent to the topic. So basically consumers will see the data as usual.
- Ordering of messages is kept, log compaction only removes some messages, but does not re-order them.
- The offset of a message is immutable (it never changes). Offset are just skipped if a message is missing.
- Deleted records can still be seen by consumers for a period of `delete.retention.ms` (by default 24 hours).

Log compaction:
- Doesn't prevent us from publishing duplicate data to Kafka.
  - De-duplication is done after a segment is committed.
  - Consumers will still read from tail as soon as the data arrives.
- Doesn't prevenet us from reading duplicate data from Kafka.
- Can fail from time to time.
  - It is an optimization and the compaction thread might crash.
  - Make sure to assign enough memory to it and that it is triggered.
  - Restart Kafka if it is broken.
- It can't be triggered by an API call.

To activate log compaction:
- `log.cleanup.policy=compact`
- `segment.ms`: max amount of time to wait to close active segment.
  - By default 1 week.
- `segment.bytes`: max size of a segment.
  - By default 1 GB.
- `min.compaction.lag.ms`: how long to wait before a message can be compacted.
  - By default 0.
- `delete.retention.ms`: wait before deleting data marked for compaction.
  - By default 24 hours.
- `min.cleanable.dirty.ratio`: higher -> less, more efficient cleaning. Lower -> opposite.
  - By default 0.5. 

# Kafka Cluster
A Kafka cluster is a group of Kafka brokers (servers) working together to provide:
- Scalable event streaming (can handle TBs of data).
- High availability (no single point of failure).
- Fault tolerance (data replicated across brokers).

At its core, a Kafka cluster:
- Stores topics (collections of messages).
- Splits topics into partitions (units of parallelism & scaling).
- Assigns partitions to brokers.
- Uses replication so data survives broker failures.

Imagine a 3-broker cluster with topic orders having 6 partitions and replication factor 3:
- Partition 0 (leader on Broker 1, replicas on Broker 2 & 3)
- Partition 1 (leader on Broker 2, replicas on Broker 1 & 3)
- Partition 2 (leader on Broker 3, replicas on Broker 1 & 2)
- … and so on.

This ensures:
- High availability → if Broker 1 fails, Partition 0’s leader moves to Broker 2.
- Scalability → multiple partitions allow parallel consumption & production.

This is how Data Flows in a Kafka Cluster
1. Producer sends a message → goes to a partition (decided by key or round-robin).
2. Leader broker stores it → writes message to disk (commit log).
3. Followers replicate the message from the leader.
4. Once acknowledged → producer considers message successfully sent.
5. Consumer reads from the leader → fetches committed messages.

## Cluster-Level Features
- Partition Rebalancing → When brokers join/leave, partitions are reassigned.
- Replication Quorum → Kafka ensures data is acknowledged by enough replicas (acks=all).
- Retention Policies → Kafka stores messages for a configurable time (e.g., 7 days).
- Monitoring → Metrics via JMX, Prometheus, Grafana.
- Security:
  - Authentication (SASL, Kerberos, OAuth).
  - Authorization (ACLs).
  - Encryption (TLS).

## Fault Tolerance and High Availability
Kafka is designed for failure. If a broker goes down:
- Controller detects failure.
- Partitions on that broker are re-assigned.
- Consumers/producers automatically redirect to new leaders.
- With replication factor 3, Kafka can survive 2 broker failures.

## Cluster Sizing and Best Practices
- Start with 3 brokers (minimum for production).
- Replication factor = 3 for critical topics.
- Partitions:
  - More partitions → higher throughput. But too many partitions → overhead (rule of thumb: start with 100 per broker).
  - Disk choice: use fast SSDs.
  - Networking: low-latency, high-bandwidth network is critical.

## Scaling Kafka Clusters
- Horizontal scaling → add more brokers.
- Rebalance partitions across new brokers.
- Use Kafka Streams or ksqlDB for processing.
- Large deployments may separate controller nodes from broker nodes (in KRaft).

## Monitoring and Maintenance
Key metrics:
- Under-replicated partitions.
- Offline partitions.
- Broker health.
- Consumer lag.

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
- Command: `kafka-configs`
- This command adds/removes entity config for a topic, client, user or broker.
- List configuration for a specific topic: `kafka-configs -bootstrap-server localhost:9092 --entity-type topics --entity-name topic_name --descbire`
- Add specific configuration for a topic (min insync replicas in the example): `kafka-configs -bootstrap-server localhost:9092 --entity-type topics --entity-name topic_name --add-config min.insync.replicas=2 --alter`
- Delete specific configuration for a topic (min insync replcias in the example): `kafka-configs -bootstrap-server localhost:9092 --entity-type topics --entity-name topic_name --delete-config min.insync.replicas=2 --alter`

To fully configure producers and consumers we have the Kafka documentation:
- [Producers](https://kafka.apache.org/documentation/#producerconfigs). Here the most important configuration:
  - `acks`:
    - 0 (no ack): no response is requested from the producer. Useful for data where it's okay to potentially losing messages, like metrics or logs. Nice in performance because the broker never replies.
    - 1 (leader ack): only the leader send the ack from the broker. If the leader goes down before the data is replicated, the data will get lost. If there is no ack, the producer may retry.
    - all (leader and replicas ack): most secure option but worse in performance, since both leader and replicas need to send the ack. There is no data loss if there are enough replicas online.
  - `min.insync.replicas`: it can be set up at broker or topic level. It is mandatory to use with acks = all. It specifies the number of replicas which must send back the ack so the leader can ack the message. For instance, if its value = 2, it means that at least 2 brokers that are ISR (including the leader) must respond that they have the data. Another example, if we use replication.factor = 3, min.insync.replicas = 2 and acks = all, we can tolerate only 1 broker going down, otherwise the producer will receive an exception on send.
  - `retries`: when we have failures on our the producer, there is an implicit retry mechanism so the message is retried automatically by the producer.
    - By default = 0 with Kafka <= 2.0 or Integer.MAX_VALUE >= 2.1.
  - `retries.backoff.ms`: number of ms between retries by the producer.
    - By default 100ms.
  - `delivery.timeout.ms`: number of ms when the message will be retried. The attempts won't happen forever, over and over again, the retry mechanism will retry the message up to this number of ms. There will be a message loss if the ack can't be achieved within the time defined in this param.
    - By default 120000 ms (2 minutes), which means the message will be retried every 100ms up to 2 minutes (around 1200 attempts).
  - `max.in.flight.requests.per.connection`: in case of retries, there is a chance that messages will be sent out of order. If we rely on key-based ordering, that can be an issue. With this parameter we can configure how many produce requests can be made in parallel.
    - By default 5.
    - Set it to 1 if we need to ensure order strictly. But it may impact throughput since we're processing just 1 message at a time.
  - `enable.idempotence`: with Kafka >= 1.0.0 we can configure our producers to be idempotent. That means that if by any chance there is a client timeout between producer and broker, which means the brocker acked the message the that ack never reached the producer, when the producer retries the message is not gonna be written again into the broker. Thanks to an internal idempotence key, the broker will know that the message was previously written and it'll send the ack right away.
  - `compression.type`: messages are sent compressed. That may impact the throughput because the messages must be sent compressed by the producer and then decompressed by the consumer. To take advantage of the compression, we need to send messages in batches (instead of 1 by 1). The bigger the size of the batch is, the more effective the compression is.
    - By default is 'none'. Potential values are 'gzip', 'lz4' and 'snappy'.
    - The compressed batch has the following advantages over the individual messages compression: much smaller producer request size, faster to transfer data over the network => less latency, better throughput, better disk utilization in Kafka (stored messages on disk are smaller).
  - `batch.size`: by default Kafka tries to send a message as soon as possible. For instance, if we can have 5 requests in parallel, if all of them are in flight, Kafka is smart enough to start batching the messages while they wait to send them all at once. This parameter indicates the size of the batch, because if a batch is full (even the linger.ms is not reached yet), the batch will be sent to Kafka right away.
    - By default 16Kb.
    - Increasing the size to 32Kb or 64Kb can help increasing the throughput, compression and efficiency of requests.
    - Any message bigger than the batch size won't be batched.
    - A batch is allocated per partition, so make sure not to set it to a number that's too high.
  - `linger.ms`: number of ms a producer is willing to wait after sending a batch out. By introducing some lag (for instance 5), we increase the chances of messages being sent together in a batch. So at the expense of introducing a small delay, we can increase throughput, compression and efficiency of our producer.
    - By default 0.
  - A safe configuration is `enable.idempotence=true` (producer level), `min.insync.replicas=2` (broker/topic level), `acks=all`, `retries=MAX_INT`, `max.in.flight.messages.per.connection=5`. Running a safe producer may impact throughput and latency, so we always need to test the configuration before running it in PRO.
- [Consumers](https://kafka.apache.org/documentation/#consumerconfigs)
  - `auto.offset.reset`: this paramater defines the behavior of the consumer when there is no committed position (which would be the case when the group is first initialized) or when an offset is out of range.
    - earliest: will read from the beginning of the log. 
    - latest: will read from the end of the log.
    - none: will throw an exception if no offset is found.
    - In case we need to replay the data again, we need to stop our consumer and restart the offsets (see reset command from the CLI section).
  - `fetch.min.bytes`: control how much data you want to pool at least on each request.
    - By default 1.
    - Help improving throughput and decreasing number of requests at the cost of latency.
  - `max.poll.records`: control how many records to receive per poll request.
    - By default 500.
    - Increase if the messages are very small and have a lot of available RAM.
  - `max.partitions.fetch.bytes`: maximum data returned by the broker per partition.
    - By default 1 MB.
    - If we read from 100 partitions, we'll need a lot of RAM.
  - `fetch.max.bytes`: maximum data returned for each request (covers multiple partitions).
    - By default 50MB.
    - The consumer performs multiple fetches in parallel.
  - `offset.retention.minutees`: number of minutes kafka keeps the offsets.
    - Default value 7 days (10080 minutes).
  - `session.timeout.ms`: the max time the broker can be without a hearbeat (health check) of a consumer before it is considered dead.
    - By default 10 seconds.
    - Set lower to faster consumer rebalances.
  - `heartbeat.interval.ms`: how often the consumer group sends the hearbeat to the broker.
    - By default 3 seconds.
    - General rule, 1/3 of session.timeout.ms.
    - This mechanism is used to detect a consumer application being down.
  - `max.poll.interval.ms`: maximum amount of time between two .poll() calls before declaring the consumer dead.
    - By default 5 minutes.
    - This is particular relevant for Big Data.
    - This mechanism is used to detect a data processing issue with the consumer.
  - Offset commitment strategies:
    1. `enable.auto.commit=true` & synchronous processing of batches. Offset will be comitted regularly for us (`auto.commit.interval.ms=5000` by default). If we don't use sync processing, we'll be in "at-most-once" behavior because offsets will be commited before the data is processed.
    2. `enable.auto.commit=false` & synchronous processing of batches. Offset will be comitted after the data is processed. Strategy at-leas-once.  

# About the example
The example linked to this tutorial consists of 3 modules and 1 folder:
1. [Local](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/local): this folder contains a docker-compose file to be able to run a Kafka & Zookeeper server locally without worrying about the installation. To run the server, just type `docker-compose up` and the Kafka server will be running in the port 9092 and Zookeeper in the 2181.  
2. [Producer](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/producer): this project exposes an API in the port 8080 to write messages to Kafka.
   - "/publishkt": the message will be sent by using the kafka template. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishkt?topic=my_topic` and the body `{"message":"myMessage"}`.
   - "/publishkp": the message will be sent by using the kafka producer. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishkp?topic=my_topic` and the body `{"message":"myMessage"}`.
3. [Consumer](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/consumer): this project exposes 3 consumers to read from Kafka.
   - The first one is called MyKafkaConsumer. This class implements the interface AcknowledgingMessageListener and is linked to the messageListenerContainer for a specific topic.
   - The second one is called KafkaConsumers. We use the Spring annotation @KafkaListener to create another consumer.
   - The third one is called ConsumerServiceImpl. The consumer is create here by using the KafkaConsumer class. A fixed thread pool is used to run this task concurrently.
4. [Twitter producer](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/twitterproducer): this projects creates a producer by using tweets from tweeter.
5. [Elastic Search consumer](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/elasticsearchconsumer): this projects creates a consumer to read tweets from the broker and save them into Elastic Search.
6. [Twitter streams](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/twitterstreams): this projects creates a stream to read tweets from the broker, filter them by using number of retweets as criteria and storing them into a new topic.
