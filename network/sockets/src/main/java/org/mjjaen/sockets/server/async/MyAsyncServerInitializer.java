package org.mjjaen.sockets.server.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyAsyncServerInitializer {
    public static Map<AsynchronousSocketChannel, String> clientsConnected = new HashMap<>();
    public static void initializeAndRunServer() {
        log.info("Launching server ...");
        try {
            AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 1235));

            Attachment attachment = new Attachment();
            attachment.setServer(serverSocket);

            log.info("Server launched.");
            serverSocket.accept(attachment, new ConnectionHandler());
            log.info("Server listening ...");

            Thread.currentThread().join();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
