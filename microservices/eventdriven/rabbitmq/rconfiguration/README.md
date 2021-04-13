# What you can find in this tutorial
This project is the core configuration for the [publisher](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/publisher) and [subscriber](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/subscriber) projects.

This project consists of 3 folders:
- businessObject: the class with the definition of the payload of the messages.
- config: this package contains a class to configure the messageConverter and the template AmqpTemplate (interface which is implemented by the RabbitTemplate class) used to send messages.
- rabbitconfig: this pakckage contains the specific configuration for each type of exchange.
  - DirectQueueConfiguration: queues, exchanges and bindings for the main queue, the retry queue and the dead queue.
  - FanoutQueueConfiguration: queues, exchange and bindings for this exchange.
  - HeaderQueueConfiguration: queues, exchange and bindings for this exchange.
  - TopicQueueConfiguration: queues, exchange and bindings for this exchange.