package org.mjjaen.sockets.client.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.UUID;

@Slf4j
public class MyAsyncClientInitializer {
    private static UUID id = UUID.randomUUID();

    public static void initializeAndRunClient() {
        log.info("Launching client ...");
        try {
            AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open();
            log.info("Client with id = " + id + " launched.");

            Attachment attachment = new Attachment();
            attachment.setClient(clientChannel);
            attachment.setByteBuffer(ByteBuffer.allocate(2048));
            attachment.setUuid(id.toString());

            clientChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 1235), attachment, new ConnectionHandler());
            Thread.currentThread().join();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
