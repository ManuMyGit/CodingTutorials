# What you can find in this tutorial  
The code creates a kafka producer to save the messages into a elastic search database (running as a docker container).

To run the elastic search container, just go to the [local/elasticsearch](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/kafka/local/elasticsearch) folder and run the docker compose file.

The project works like this:
- Two type of consumers are available: one-by-one consumer or batch consumer. The consumer used can be configure in the application.properties file, property consumer.strategy.type. The values are defined in the factory MyKafkaConsumerFactory class.

What the Kafka consumer does is to read the messages and to send them to Elastic Search by using the instance of the ElasticSearchService interface. The inteerface ofers 3 methods:
- createClient(): it creates and return an Elastic Search client.
- insertData(): it inserts 1 record in Elastic Search.
- insertDataBulk(): it inserts a set of records in Elastic Search.

The Kafka consumer is configured in the KafkaConfiguration class. The main features are:
- It is configured to trust the packages sent by the producer since both producer and consumer are different projects.
- The maximum records retrieved by .poll() are set to 10. That way, the consumer will be able to consumer up to 10 messages per poll(). This is not an optimal configuration, it is just to show in the logs how the 10 is a limit.
- A ConcurrentMessageListenerContainer is used with 3 threads. Since there are 3 partitions in the topic, each thread will take care of 1 partition. This will make our cosumer faster.