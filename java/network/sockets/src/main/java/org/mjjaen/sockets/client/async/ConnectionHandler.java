package org.mjjaen.sockets.client.async;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class ConnectionHandler implements CompletionHandler<Void, Attachment> {
    @Override
    public void completed(Void result, Attachment attachment) {
        Attachment readAttachment = new Attachment();
        readAttachment.setClient(attachment.getClient());
        readAttachment.setByteBuffer(ByteBuffer.allocate(2048));

        Attachment writeAttachment = new Attachment();
        writeAttachment.setClient(attachment.getClient());
        writeAttachment.setByteBuffer(ByteBuffer.allocate(2048));
        writeAttachment.setScanner(new Scanner(System.in));

        //Read date from server
        attachment.getClient().read(readAttachment.getByteBuffer(), readAttachment, new ReadHandler());

        log.info("Sending client id to the server ...");
        Charset cs = StandardCharsets.UTF_8;
        String msg = "id=" + attachment.getUuid();
        byte[] data = msg.getBytes(cs);
        writeAttachment.getByteBuffer().put(data);
        writeAttachment.getByteBuffer().flip();
        //Initialize sending stream by sending the id
        attachment.getClient().write(writeAttachment.getByteBuffer(), writeAttachment, new WriteHandler());

        Thread.currentThread().interrupt();
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {

    }
}
