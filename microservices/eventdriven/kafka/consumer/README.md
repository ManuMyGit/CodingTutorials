# What you can find in this tutorial
This project consists of several classes to read the messages from the Kafka broker:
- KafkaConfiguration: configuration class where the KafkaConsumer and the factories are configured.
- ConsumerServiceImpl: the consumer is create here by using the KafkaConsumer class. A fixed thread pool is used to run this task concurrently.
- KafkaConsumers: this class consists of one listeners by using the Spring annotation @KafkaListener.
- MyKafkaConsumer: this class defines another by implementing the interface AcknowledgingMessageListener.