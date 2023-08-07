# Introduction
Sockets refers to writing programs that execute across multiple computers in which the devices are all connected to each other using a network.

There are two communication protocols that we can use for socket programming: User Datagram Protocol (UDP) and Transfer Control Protocol (TCP).

The main difference between the two is that UDP is connection-less, meaning there's no session between the client and the server, while TCP is connection-oriented, meaning an exclusive connection must first be established between the client and server for communication to take place.

This tutorial presents an introduction to sockets programming over TCP/IP networks, and demonstrates how to write client/server applications in Java.

# Server & Client
Sockets follow client server design pattern, where a server is used to create and manage TCP client socket connections. The server binds to a port and waits for new TCP client connections. The client connects to the server through the port exposed by the server.

# Synchronous vs asynchronous
In a synchronous communication model, both client and server follow a request-response pattern. The client sends a request and waits for a response before proceeding.

Synchronous communication pros:
- Simplicity in coding and understanding.
- Straightforward error handling.

Cons:
- Can lead to blocked threads if the server takes time to respond.
- Not suitable for handling a large number of simultaneous clients.

Asynchronous communication allows clients and servers to perform tasks concurrently without blocking. Java provides java.nio classes to implement asynchronous sockets.

Asynchronous communication pros:
- Better scalability, as a single thread can handle multiple clients.
- Improved performance for handling many connections simultaneously.

Cons:
- More complex to implement compared to synchronous communication.
- Requires careful handling of concurrency and synchronization.

# Executing the example
This tutorial offers two type of sockets:
- Synchronous
- Asynchronous

To execute the synchronous configuration:
```java
mvn package

java -jar target/sockets-1.0.0-RELEASE.jar server

java -jar target/sockets-1.0.0-RELEASE.jar client
```

To execute the asynchronous configuration:
```java
mvn package

java -jar target/sockets-1.0.0-RELEASE.jar asyncserver

java -jar target/sockets-1.0.0-RELEASE.jar asyncclient
```

This is what the synchronous server does:
1. Create a threadpool to manage multiple connections.
2. Wait to accept a new connection (```Socket socket = serverSocket.accept();```). This is a blocking instruction.
3. Once a new connection is accepted, a new Runnable (```ClientHhandler```) is submitted to the threadpool. This means that there's a new thread running per client connection.
4. The server broadcasts the new client connection to the rest of the clients.
5. The ClientHandler sends a message with today's date to the client.
6. It enters into an infinite loop to listen for messages.
7. Once a new message is received, it prints the message and broadcasts it to the rest of the clients.
8. If the message is bye, the server terminates the connection with the client and broadcast the termination to the rest of the clients.

This is what the synchronous client does:
1. Once it is created, it assigns itself and ID which will be sent to the server.
2. It creates a new threadpool with just 1 thread, used to listen from messages from the server.
3. Within the main thread, it sends the ID to the server and scan for inputs from keyboard.
4. When input is detected, it sends it to the server. If input is bye, it closes the connection with the server.

This is what the asynchronous server does:
1. Creates an asynchronous server socket channel and binds it to the server port.
2. Creates a new attachment used to manage new connections.
3. New successfully accepted connections are managed by the ```ConnectionHandler```.
4. After completed connection:
   1. The server is prepared to accept a new connection.
   2. A new attachment is created to read.
   3. A read operation is launched, managed by the ```ReadHandler```.
   4. A new attachment is created to write.
   5. A write operation is launched, managed by the ```WriteHandler```.
5. The ```ReadHandler```, after completed operation, validates whether the connection is terminated.
   1. If terminated, broadcast termination to the rest of the clients.
   2. If not terminated, it reads the message, prints it and broadcasts it to the rest of the clients.
   3. For the original connection, it takes the id and broadcasts the new connection to the rest of the clients.

This is what the asynchronous client does:
1. It creates an asynchronous socket channel.
2. It creates an attachment with the information required connecting successfully to the server.
3. It connects to the server, assigning a ```ConnectionHandler``` to manage successful connection.
4. After successful connection:
   1. A new ```ReadHandler``` is created to read from the server.
   2. A new ```WriteHandler``` is created to wring to the server.