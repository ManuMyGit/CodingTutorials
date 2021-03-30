# Intro  
In this tutorial we're gonna show the difference between synchronous and asynchronous request processing on the server side. To do that, we're building a simple rest api within an Undertow server.  
  
# [Undertow](http://undertow.io/) server  
Undertow is a flexible performant web server written in java, providing both blocking and non-blocking API’s based on NIO.  
Without going into details, we'll talk about Undertow threads, how many types it has and the characteristics of each one.  
  
## Architecture  
An Undertow server is basically composed of three things, one (or more) XNIO worker instance, one or more connectors, and a handler chain to handle incoming requests.  
  
## XNIO  
Undertow is based on XNIO. The XNIO project provides a thin abstraction layer over Java NIO. The XNIO worker manages both the IO threads, and a thread pool that can be used for blocking tasks. In general non-blocking handlers will run from within an IO thread, while blocking tasks such as Servlet invocations will be dispatched to the worker thread pool. We'll focus on XNIO workers.  
  
## Listeners  
The concept of a listener in Undertow is basically the part of Undertow that handles incoming connections, and the underlying wire protocol. By default Undertow ships with 5 different listeners:  
- HTTP/1.1  
- HTTPS  
- AJP  
- SPDY (deprecated)  
- HTTP/2  
  
Listeners represent the entry point of an Undertow application. All incoming requests will come through a listener, and a listener is responsible for translating a request into an instance of the `HttpServerExchange` object, and then turning the result into a response that can be sent back to the client.  
  
## XNIO Workers  
All listeners are tied to an XNIO Worker instance. Usually there will only be a single worker instance that is shared between listeners, however it is possible to create a new worker for each listener. The worker instance manages the listeners IO threads, and also the default blocking task thread pool.  
 - Default number of workers: 8 * Number of logical cores of the CPU. For instance, in a computer with 4 cores and with 2 logical threads per core, the number of workers will be 64.  
  
Undertow relies on the XNIO API for creating Worker threads using bounded-queue-thread-pool. The bounded-queue-thread-pool thread pool executor has:  
 - A core (core-max-threads)  
 - A maximum size (task-max-threads)  
 - A specified queue length.  
  
If the number of running threads is less than the core size when a task is submitted, a new thread will be created; otherwise, it will be put in the queue. If the queue's maximum size has been reached and the maximum number of threads hasn't been reached, a new thread is also created. If max-threads is hit, the call will be sent to the handoff-executor. If no handoff-executor is configured, the call will be discarded. That being said, provided that there are enough io-threads to serve your http request, the core-max-threads (first) and the task-max-threads (after) are used to determine if the request is served or if it is going to be discarded.  
  
In this example, the number of workers of the embedded Undertow can be modified using the property server.undertow.worker-threads in the application.properties file.  
  
### Buffer Pool  
All listeners have a buffer pool, which is used to allocate pooled NIO  `ByteBuffer` instances. These buffers are used for IO operations, and the buffer size has a big impact on application performance. For servers the ideal size is generally 16k. In this tutorial, the buffer size can be modified using the property server.undertow.buffer-size in the application.properties file.  
  
# Synchronous example  
In this example, a background process has been simulated with a sleep of 2 seconds (it could be equivalent to a data access, a call to another service, ...). The project is deployed in the port 8080 by default, and the url is /sync. In my computer, the number of workers is 96. It means the server can manage in parallel 96 requests. If a JMeter is configured to call the API 96 **times at the same time**, all of them will be returned in 2 seconds more or less. However, if we configure the test with 97 requests, the last one will be placed in the server buffer until a worker finishes. It means the request number 97 will be returned to the client in 4 seconds more or less (2 seconds waiting in the buffer + 2 seconds processing). If we configure JMeter to have a timeout of 2200 ms, no matter how many calls we configure to be run at the same time, only 96 will be marked successfully by the test.  
  
# Asynchronous example  
In this example, the same background process is used. However, this process is called asynchronously from the controller using the CompletableFuture feature introduced in Java 8. To do that, a new thread pool has been created. It can be configured in the SyncasyncserverApplication class, and these are the main parameters:  
 - corePoolSize: initial pool size. Default 1.  
 - maxPoolSize: maximum size of the pool.  
 - queueCapacity: pool queue size. Default Integer.MAX_VALUE.  
  
Apart from that, a log has been included in the controller to check the time it takes to process the request. Basically, it takes milliseconds, which means this thread will be free to handle another request much faster then in the synchronous example. However, the bottleneck is now the new pool. The request won't be able to be returned to the client until the background process is finished. Once the background process is finished, another thread will take the control and will return the response to the client (again in milliseconds). Basically in this example the number of workers is practically irrelevant, the performance of the API will depend directly of the new pool to manage the asynchronous calls. We could reduce the workers to 10 and, provided the number of threads in the new pool is equal to the number of workers in the sync. example, the performance will be the same.  
  
However, the conclusion is that this API is not totally asynchronous, eventually there is a resource waiting for the process to finish to send the response to the client. So the question is, what to do to get a real non blocking/asynchronous API? The response will lead as to other of the examples of this repository, Spring 5 reactive programming.  
  
# How to run the test  
To run the code the only thing needed is:  
1. To download the source. In order to isolate the test with regards to the computer features, the maximum number of workers has been configured in the application.propertites file up to 10.  
2. To run the server.  
3. To open the jmeter files with JMeter.  
4. To run the JMeter test.  
  
The JMeter test consists of 2 Thread Groups:  
1. Synchronous requests: 11 users have been created and a timeout of 2200ms has been configured. Due to the fact that the manager takes 2000ms to run the task, only 10 request will be managed at the same time, having a client timeout in the request 11 (that can be checked in the results tree in JMeter).  
2. Asynchronous requests: 50 users have been created and the same timeout of 2200ms has been configured. Under the same circumstances, JMeter doesn't show any error. Why? Because of the async behavior of this controller. Even though there are only 10 workers available in the server, what they do is just to take the request and to send them to the manager, which is executed asynchronously in the ThreadPoolTaskExecutor. This way, the worker is ready to handle another request. The executor has been configured with a maximum pool size of 100, and a queue capacity of 1000. So we could run the same test with 100 users and would still get 0 errors.

In the Async test we can see two different logs:
- `XNIO-1 task-1`: with the default configuration of the project, the number of task won't be higher than 10. The reason of that is that we're limiting the number of workers to 10. However, we can see that with the default JMeter configuration (50 concurrent users) the same worker manages more than 1 request, which makes totally sense because in the moment the worker sends the request to the manager (executed in another thread), it gets free and it is ready to handle a new request.
- `Async-Exec-1`:  with the default configuration of the project, 100 threads are available in the async pool. Since we're running 50 requests at the same time, 50 threads of the pool will be used, so we'll be able to see the log Async-Exec-50.