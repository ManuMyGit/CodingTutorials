package org.mjjaen.sockets.server.sync;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class MyServerInitializer {
    public static void initializeAndRunServer() throws IOException {
        log.info("Launching server ...");
        ServerSocket serverSocket = new ServerSocket(1234);
        MyServer myServer = new MyServer(serverSocket);
        log.info("Server launched.");
        log.info("Server listening ...");
        myServer.startServer();
    }
}
