# Event driven pattern
Event-driven architecture is a software architecture and model for application design. With an event-driven system, the capture, communication, processing, and persistence of events are the core structure of the solution. This differs from a traditional request-driven model where the communication between systems happen synchronously based on request-response.

Event-driven architecture enables minimal coupling, which makes it a good option for modern, distributed application architectures. An event-driven architecture is loosely coupled because event producers don’t know which event consumers are listening for an event, and the event doesn’t know what the consequences are of its occurrence.

## What's an event?
An event is any significant occurrence or change in state for system hardware or software. An event is not the same as an event notification, which is a message or notification sent by the system to notify another part of the system that an event has taken place. The source of an event can be from internal or external inputs. Events can generate from a user, like a mouse click or keystroke, an external source, such as a sensor output, or come from the system, like loading a program.

## How does event-driven architecture work?
Event-driven architecture is made up of event producers and event consumers. An event producer detects or senses an event and represents the event as a message. It does not know the consumer of the event, or the outcome of an event.  After an event has been detected, it is transmitted from the event producer to the event consumers through event channels, where an event processing platform processes the event asynchronously. Event consumers need to be informed when an event has occurred. They might process the event or may only be impacted by it. 

The event processing platform will execute the correct response to an event and send the activity downstream to the right consumers. This downstream activity is where the outcome of an event is seen. 

## Benefits of event-driven architecture
An event-driven architecture can help organizations achieve a flexible system that can adapt to changes and make decisions in real time. Real-time situational awareness means that business decisions, whether manual or automated, can be made using all of the available data that reflects the current state of your systems. 

Events are captured as they occur from event sources such as Internet of Things (IoT) devices, applications, and networks, allowing event producers and event consumers to share status and response information in real time. 

Organizations can add event-driven architecture to their systems and applications to improve the scalability and responsiveness of applications and access to the data and context needed for better business decisions.

## Event-driven architecture models
An event driven architecture may be based on either a pub/sub model or an event stream model.

# Publiser - Subscriber pattern
The Publish/Subscribe pattern is an architectural design pattern that provides a framework for exchanging messages between publishers and subscribers. This pattern involves the publisher and the subscriber relying on a message broker that relays messages from the publisher to the subscribers. The host (publisher) publishes messages (events) to a channel that subscribers can then sign up to.

Although Pub/Sub is based on earlier design patterns like message queuing and event brokers, it is more flexible and scalable. The key to this is the fact Pub/Sub enables the movement of messages between different components of the system without the components being aware of each other’s identity.

## Context and problem
In cloud-based and distributed applications, components of the system often need to provide information to other components as events happen. Asynchronous messaging is an effective way to decouple senders from consumers, and avoid blocking the sender to wait for a response. However, using a dedicated message queue for each consumer does not effectively scale to many consumers. Also, some of the consumers might be interested in only a subset of the information. How can the sender announce events to all interested consumers without knowing their identities?

## Solution
Introduce an asynchronous messaging subsystem that includes the following:
- An input messaging channel used by the sender. The sender packages events into messages, using a known message format, and sends these messages via the input channel. The sender in this pattern is also called the publisher.
- One output messaging channel per consumer. The consumers are known as subscribers.
- A mechanism for copying each message from the input channel to the output channels for all subscribers interested in that message. This operation is typically handled by an intermediary such as a message broker or event bus.

## Benefits
Pub/sub messaging has the following benefits:
- It decouples subsystems that still need to communicate. Subsystems can be managed independently, and messages can be properly managed even if one or more receivers are offline.
- It increases scalability and improves responsiveness of the sender. The sender can quickly send a single message to the input channel, then return to its core processing responsibilities. The messaging infrastructure is responsible for ensuring messages are delivered to interested subscribers.
- It improves reliability. Asynchronous messaging helps applications continue to run smoothly under increased loads and handle intermittent failures more effectively.
- It allows for deferred or scheduled processing. Subscribers can wait to pick up messages until off-peak hours, or messages can be routed or processed according to a specific schedule.
- It enables simpler integration between systems using different platforms, programming languages, or communication protocols, as well as between on-premises systems and applications running in the cloud.
- It facilitates asynchronous workflows across an enterprise.
- It improves testability. Channels can be monitored and messages can be inspected or logged as part of an overall integration test strategy.
- It provides separation of concerns for your applications. Each application can focus on its core capabilities, while the messaging infrastructure handles everything required to reliably route messages to multiple consumers.

## When to use this pattern
Use this pattern when:
- An application needs to broadcast information to a significant number of consumers.
- An application needs to communicate with one or more independently-developed applications or services, which may use different platforms, programming languages, and communication protocols.
- An application can send information to consumers without requiring real-time responses from the consumers.
- The systems being integrated are designed to support an eventual consistency model for their data.
- An application needs to communicate information to multiple consumers, which may have different availability requirements or uptime schedules than the sender.

## When not to use this pattern
This pattern might not be useful when:
- An application has only a few consumers who need significantly different information from the producing application.
- An application requires near real-time interaction with consumers.

## Issues and considerations
Consider the following points when deciding how to implement this pattern:
- Existing technologies. It is strongly recommended to use available messaging products and services that support a publish-subscribe model, rather than building your own. Available technologies that can be used for pub/sub messaging include Redis, RabbitMQ, and Apache Kafka. In this case, we're using RabbitMQ.
- Subscription handling. The messaging infrastructure must provide mechanisms that consumers can use to subscribe to or unsubscribe from available channels.
- Security. Connecting to any message channel must be restricted by security policy to prevent eavesdropping by unauthorized users or applications.
- Subsets of messages. Subscribers are usually only interested in subset of the messages distributed by a publisher. Messaging services often allow subscribers to narrow the set of messages received by:
  - Topics. Each topic has a dedicated output channel, and each consumer can subscribe to all relevant topics.
  - Content filtering. Messages are inspected and distributed based on the content of each message. Each subscriber can specify the content it is interested in.
- Wildcard subscribers. Consider allowing subscribers to subscribe to multiple topics via wildcards.
- Bi-directional communication. The channels in a publish-subscribe system are treated as unidirectional. If a specific subscriber needs to send acknowledgment or communicate status back to the publisher, consider using the Request/Reply Pattern. This pattern uses one channel to send a message to the subscriber, and a separate reply channel for communicating back to the publisher.
- Message ordering. The order in which consumer instances receive messages isn't guaranteed, and doesn't necessarily reflect the order in which the messages were created. Design the system to ensure that message processing is idempotent to help eliminate any dependency on the order of message handling.
- Message priority. Some solutions may require that messages are processed in a specific order. The Priority Queue pattern provides a mechanism for ensuring specific messages are delivered before others.
- Poison messages. A malformed message, or a task that requires access to resources that aren't available, can cause a service instance to fail. The system should prevent such messages being returned to the queue. Instead, capture and store the details of these messages elsewhere so that they can be analyzed if necessary.
- Repeated messages. The same message might be sent more than once. For example, the sender might fail after posting a message. Then a new instance of the sender might start up and repeat the message. The messaging infrastructure should implement duplicate message detection and removal (also known as de-duping) based on message IDs in order to provide at-most-once delivery of messages.
- Message expiration. A message might have a limited lifetime. If it isn't processed within this period, it might no longer be relevant and should be discarded. A sender can specify an expiration time as part of the data in the message. A receiver can examine this information before deciding whether to perform the business logic associated with the message.
- Message scheduling. A message might be temporarily embargoed and should not be processed until a specific date and time. The message should not be available to a receiver until this time.

# About this module
In this documentation several implementations of this pattern can be found:
 - [RabbitMQ Pub/Sub](https://github.com/ManuMyGit/CodingTutorials/tree/main/microservices/eventdriven/rabbitmq): complete example about this pattern built in Java using RabbitMQ as broker.
 - Kafka Pub/Sub (coming soon ...)
 - Kafka streaming (coming soon ...)