package org.mjjaen.sockets.client.async;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
public class ReadHandler implements CompletionHandler<Integer, Attachment> {
    @Override
    public void completed(Integer result, Attachment attachment) {
        Charset cs = StandardCharsets.UTF_8;
        attachment.getByteBuffer().flip();
        String message = new String(attachment.getByteBuffer().array(), cs).trim();
        if(message != null && message.trim().length() > 0) {
            log.info("Read message: " + new String(attachment.getByteBuffer().array()));
            attachment.getByteBuffer().clear();
        }
        Arrays.fill(attachment.getByteBuffer().array(), (byte)0);
        attachment.getByteBuffer().clear();
        attachment.getClient().read(attachment.getByteBuffer(), attachment, this);
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {

    }
}
