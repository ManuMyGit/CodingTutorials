# What you can find in this tutorial  
The code exposes a REST API to send messages to a Kafka broker exposed in the localhost. The endpoint is exposed in the port 8080.

The controller manages 2 endpoints:
- `/publishkt?topic=my_topic&key=my_key`: it publishes the message by using a kafta template. The topic is required. They key is optional. The consumer is currently configured to read from topic "my_topic".
- `/publishkp?topic=my_topic&key=my_key`: same as above, but using a Kafka producer.

The body of the message is just a simple Json with a field. Ex: `{"message":"any message"}`.

The logic to send the messages can be found inside the ProducerServiceImpl class:  
- The method sendMessage makes use of a kafkaProducer or kafkaTemplate object depending on the endpoint.
- Both the KafkaProducer and the KafkaTemplate beans are created in the KafkaConfiguration class.

Before using the API, we recommend to get the topic created in the broker (the topic is part of the request). To do that, we can use the following command to create a topic with 3 partitions and replication factor 1 (because there is just 1 broker running) within the broker:
- `kafka-topics --bootstrap-server localhost:9092 --topic my_topic --create --partitions 3 --replication-factor 1`