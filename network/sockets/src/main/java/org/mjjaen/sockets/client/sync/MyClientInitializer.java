package org.mjjaen.sockets.client.sync;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@Slf4j
public class MyClientInitializer {
    public static void initializeAndRunClient() throws IOException {
        log.info("Launching client ...");
        Socket socket = new Socket(InetAddress.getLocalHost(), 1234);
        MyClient myClient = new MyClient(socket);
        log.info("Client with id = " + myClient.getId().toString() + " launched.");
        myClient.startClient();
    }
}
