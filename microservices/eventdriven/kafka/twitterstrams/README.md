# What you can find in this tutorial  
The code creates a Kafka stream to filter the twitter with a specific number of retweets (specified in the application.properties file). The messages are sent to a new topic my_topic_streams.

Before using the API, we recommend to get the topic created in the broker (the topic is part of the request). To do that, we can use the following command to create a topic with 3 partitions and replication factor 1 (because there is just 1 broker running) within the broker:
- `kafka-topics --bootstrap-server localhost:9092 --topic twitter_topic_streams --create --partitions 3 --replication-factor 1`