# What you can find in this tutorial
The code exposes a REST API to send messages to a Rabbit MQ broker exposed in the localhost. The endpoint is exposed in the port 8080.

The controller manages 4 endpoints:
- `/publishFanout`: it publishes the message by using a fanout exchange.
- `/publishTopic`: it publishes the message by using a topic exchange.
- `/publishDirect`: it publishes the message by using a direct exchange.
- `/publishHeader`: it publishes the message by using a header exchange.

The body of the message is just a simple Json with a field. Ex: `{"message":"any message"}`.

The logic to send the messages can be found inside the PublishServiceImpl class:
- The method sendMessage makes use of the rabbitTemplate object to send the messages.
- The rabbitTemplate object is created in the [rconfiguration](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/configuration) proyect. 