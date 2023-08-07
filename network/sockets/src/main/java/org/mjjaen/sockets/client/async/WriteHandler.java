package org.mjjaen.sockets.client.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Slf4j
public class WriteHandler implements CompletionHandler<Integer, Attachment> {
    @Override
    public void completed(Integer result, Attachment attachment) {
        Charset cs = StandardCharsets.UTF_8;
        String message = attachment.getScanner().nextLine();
        byte[] data = message.getBytes(cs);
        attachment.getByteBuffer().clear();
        attachment.getByteBuffer().put(data);
        attachment.getByteBuffer().flip();
        if(message.compareTo("bye") == 0) {
            try {
                attachment.getClient().close();
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            attachment.getClient().write(attachment.getByteBuffer(), attachment, this);
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {

    }
}
