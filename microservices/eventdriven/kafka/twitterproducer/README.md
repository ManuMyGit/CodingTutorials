# What you can find in this tutorial  
The code creates a Twitter client by using the com.twitter.hbc-core project to send messages to a Kafka broker exposed in localhost. For the project to run we must have Twitter API credentials. They must be set up in the properties file:
- api.key
- api.secret.key
- access.token
- secret.access.token

The project works like this:
- In the main class, the method run is executed.
- The method creates a conection to Twitter by using the TwitterService object. We can send the words we want to search in Twitter.
- The messages are mapped into the Twee and User classes (the tweet json retrieve has a lot of more information, but only that one was selected).
- Messages are sent to Kafka via the KafkaService object. The message sent is an instance of the KafkaMessage class.
- The properties used to configure the producer can be located in the KafkaConfiguration class.

Before using the API, we recommend to get the topic created in the broker (the topic is part of the request). To do that, we can use the following command to create a topic with 3 partitions and replication factor 1 (because there is just 1 broker running) within the broker:
- `kafka-topics --bootstrap-server localhost:9092 --topic twitter_topic_streams --create --partitions 3 --replication-factor 1`