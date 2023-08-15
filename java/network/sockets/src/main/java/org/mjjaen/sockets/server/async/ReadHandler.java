package org.mjjaen.sockets.server.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
public class ReadHandler implements CompletionHandler<Integer, Attachment> {
    @Override
    public void completed(Integer result, Attachment attachment) {
        if (result == -1) {
            try {
                attachment.getClient().close();
                log.info("Stopped listening to the client " + MyAsyncServerInitializer.clientsConnected.get(attachment.getClient()));
                broadcastMessage("Client with id = " + MyAsyncServerInitializer.clientsConnected.get(attachment.getClient()) + " left the chat.", attachment);
                MyAsyncServerInitializer.clientsConnected.remove(attachment.getClient());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

        Charset cs = StandardCharsets.UTF_8;
        attachment.getByteBuffer().flip();
        String message = new String(attachment.getByteBuffer().array(), cs).trim();
        if(message.contains("id=")) {
            MyAsyncServerInitializer.clientsConnected.put(attachment.getClient(), message.substring(message.indexOf("=") + 1));
            log.info("Clients connected: " + MyAsyncServerInitializer.clientsConnected.size());
            broadcastMessage("Client with id = " + message + " just joined.", attachment);
        } else {
            log.info("Client with id = " + MyAsyncServerInitializer.clientsConnected.get(attachment.getClient()) + " says: " + message);
            broadcastMessage(MyAsyncServerInitializer.clientsConnected.get(attachment.getClient()) + " said: " + message, attachment);

        }
        Arrays.fill(attachment.getByteBuffer().array(), (byte)0);
        attachment.getByteBuffer().clear();
        attachment.getClient().read(attachment.getByteBuffer(), attachment, this);
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        exc.printStackTrace();
    }

    public void broadcastMessage(String message, Attachment attachment) {
        MyAsyncServerInitializer.clientsConnected.forEach((k, v) -> {
            if (k != attachment.getClient()) {
                Charset cs = StandardCharsets.UTF_8;
                byte[] data = message.getBytes(cs);
                attachment.getByteBuffer().clear();
                attachment.getByteBuffer().put(data);
                attachment.getByteBuffer().flip();
                k.write(attachment.getByteBuffer(), attachment, new WriteHandler());
            }
        });
    }
}
