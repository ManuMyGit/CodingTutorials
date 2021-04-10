# RabbitMQ

## Definitions
- Exchange: messages are not published directly to a queue. Instead, the producer sends messages to an exchange. Exchanges are message routing agents, defined by the virtual host within RabbitMQ. An exchange is responsible for routing the messages to different queues with the help of header attributes, bindings, and routing keys.
- Binding: a binding is a "link" that we set up to bind a queue to an exchange.
- Routing key: the routing key is a message attribute the exchange looks at when deciding how to route the message to queues (depending on exchange type).

## Configuration
Exchanges, connections, and queues can be configured with parameters such as durable, temporary, and auto delete upon creation. Durable exchanges survive server restarts and last until they are explicitly deleted. Temporary exchanges exist until RabbitMQ is shut down. Auto-deleted exchanges are removed once the last bound object is unbound from the exchange.

An other interesting parameter to configure is the dead letter queue. That kind of queue is used to redirect messages from its main queue in case of the following scenarios:
- The main queue is full.
- The message in the main queue expired due to its TTL.
- The message is negatively acknowledged by a consumer.

When that happens, the message is redirected to the dead letter queue. This mechanism is really useful to implement for instance a retry logic. It would be as easy as to link the main queue to its dead letter queue, then to link the dead letter queue to the main queue and to set a TTL in the dead letter queue. That way, when the message gets to the dead letter queue, after the TTL it'll be resent to the main queue again.

## Standard message flow
This is the standard message flow:
1. The producer publishes a message to the exchange.
2. The exchange receives the message and is now responsible for the routing of the message.
3. Binding must be set up between the queue and the exchange. The exchange routes the message into the queues.
4. The messages stay in the queue until they are handled by a consumer (or TTL is reached).
5. The consumer handles the message. In case there is more than 1 consumer per queue, the Round Robin algorithm takes care of delivering the messages to the different consumers subscribed to the same queue.

## Exchanges
In RabbitMQ, there are four different types of exchanges that route the message differently using different parameters and bindings setups. Clients can create their own exchanges or use the predefined default exchanges which are created when the server starts for the first time.

### Direct exchange
A direct exchange delivers messages to queues based on a message routing key. The routing key is a message attribute added to the message header by the producer. Think of the routing key as an "address" that the exchange is using to decide how to route the message. A message goes to the queue(s) with the binding key that exactly matches the routing key of the message. Another way to say it is: a message goes to the queues whose binding key exactly matches the routing key of the message.

The direct exchange type is useful to distinguish messages published to the same exchange using a simple string identifier. The default exchange AMQP brokers must provide for the direct exchange is "amq.direct".

### Fanout exchange
A fanout exchange copies and routes a received message to all queues that are bound to it regardless of routing keys or pattern matching as with direct and topic exchanges. The keys provided will simply be ignored. Fanout exchanges can be useful when the same message needs to be sent to one or more queues with consumers who may process the same message in different ways.

The default exchange AMQP brokers must provide for the topic exchange is "amq.fanout".

### Topic exchange
Topic exchanges route messages to queues based on wildcard matches between the routing key and the routing pattern, which is specified by the queue binding. Messages are routed to one or many queues based on a matching between a message routing key and this pattern.

The routing key must be a list of words, delimited by period (.). Examples are `agreements.us` and `agreements.eu.stockholm` which in this case identifies agreements that are set up for a company with offices in lots of different locations. The routing patterns may contain an asterisk (`"*"`) to match a word in a specific position of the routing key (e.g. a routing pattern of "`agreements.*.*.b.*`" only match routing keys where the first word is "`agreements`" and the fourth word is "`b`"). A pound symbol (“`#`”) indicates a match of zero or more words (e.g., a routing pattern of "`agreements.eu.berlin.#`" matches any routing keys beginning with "`agreements.eu.berlin`").

The consumers indicate which topics they are interested in (like subscribing to a feed for an individual tag). The consumer creates a queue and sets up a binding with a given routing pattern to the exchange. All messages with a routing key that match the routing pattern are routed to the queue and stay there until the consumer consumes the message.

The default exchange AMQP brokers must provide for the topic exchange is "amq.topic".

### Headers exchange
A headers exchange routes messages based on arguments containing headers and optional values. Headers exchanges are very similar to topic exchanges, but route messages based on header values instead of routing keys. A message matches if the value of the header equals the value specified upon binding.

A special argument named "x-match", added in the binding between exchange and queue, specifies if all headers must match or just one. Either any common header between the message and the binding count as a match, or all the headers referenced in the binding need to be present in the message for it to match. The "x-match" property can have two different values: "any" or "all", where "all" is the default value. A value of "all" means all header pairs (key, value) must match, while value of "any" means at least one of the header pairs must match. Headers can be constructed using a wider range of data types, integer or hash for example, instead of a string. The headers exchange type (used with the binding argument "any") is useful for directing messages which contain a subset of known (unordered) criteria.

The default exchange AMQP brokers must provide for the topic exchange is "amq.headers".

## About the example
The example linked to this tutorial consists of 3 modules and 1 folder:
1. [Configuration](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/configuration): this module has the code needed to configure the exchanges, queues and bindings in RabbitMQ, as well as the object message sent in the event.
2. [Local](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/local): this folder contains a docker-compose file to be able to run a RabbitMQ server locally without worrying about the installation. To run the server, just type `docker-compose up` and the server will be running in the port 5672 and and UI management in the 15672.
3. [Publisher](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/publisher): this project exposes 4 different APIS running in the port 8080:
   - "/publishFanout": the message will be sent by using a fanout exchange. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishFanout` and the body `{"message":"myMessage"}`.
   - "/publishTopic": the message will be sent by using a topic exchange. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishTopic` and the body `{"message":"myMessage"}`. The queues are configured so the message reaches to 2 queues.
   - "/publishDirect": the message will be sent by using a direct exchange. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishDirect` and the body `{"message":"myMessage"}`.
   - "/publishHeader": the message will be sent by using a header exchange. To use the endpoint, just run a POST call with the URL `http://localhost:8080/publishHeader?department=payment` and the body `{"message":"myMessage"}`. The available headers are payment, finance and delivery.
   - What the service does internally is to send the messages by using the AmqpTemplate configured in the configuration module.
4. [Subscriber](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq/subscriber): this project exposes 4 listeners running in the port 8081:
   - The first one is called MyListener. This listener listen to the DELIVERY_QUEUE_HEADER queue configured in the configuration module. This is a queue bound to a header exchange. The management of the messages in configured in the ListenerConfiguration class.
   - The second one is called MyMessageListener. There are two different listeners inside this class, both of them configured with the @RabbitListener annotation. They listen to the PAYMENT_QUEUE_HEADER and FINANCE_QUEUE_HEADER queues.
   - The third one is called MyMessageListenerRetry. Inside this listener a retry policy has been implemented by using the concept of dead letter queue. Basically, the queue `direct_queue` is linked to the `direct_queue_retry` queue as a dead letter queue. that means that if there is no ACK for the message, the queue is full (it a limit has been set) the message expires due to its TTL, it'll go directly to the dead letter queue. That queue is configured to have a TTL of 2 seconds, and its dead letter queue is again the `direct_queue`. What we achieve with this configuration is that the message will wait in the `direct_queue_retry` queue 1 seconds, it'll be sent again to the main queue and will be processed again. Eventually, to avoid a infinite loop of processing, we make use of the xDeath object to count the number of times the event has been processed. When it reaches the limit, we send the event to another queue, called dead queue, where the event will remain until is analyzed manually. This configuration is not 100% functional. If for instance, if we set a message limit to the main queue, when the queue is full the message will be sent to the dead letter queue. Over there, it'll wait 1 second and it'll be sent again to the main queue which, if is still full, will discard the message automatically. To force the retry mechanism, the message sent must be any different than `{"message":"OK"}`. If the message sent is the string OK, the ACK will be sent in the 1st attempt.