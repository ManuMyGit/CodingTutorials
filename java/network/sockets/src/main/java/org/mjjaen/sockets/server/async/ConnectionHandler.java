package org.mjjaen.sockets.server.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Attachment> {
    @Override
    public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Attachment attachment) {
        try {
            //A connection is accepted
            SocketAddress clientAddr = asynchronousSocketChannel.getRemoteAddress();
            log.info("Accepted a connection from " + clientAddr);

            //Accept next connection
            if (attachment.getServer().isOpen()){
                attachment.getServer().accept(attachment, this);
            }

            //Prepare to read & write from client
            Attachment readAttachment = new Attachment();
            readAttachment.setClient(asynchronousSocketChannel);
            readAttachment.setByteBuffer(ByteBuffer.allocate(2048));

            //Receive ID
            asynchronousSocketChannel.read(readAttachment.getByteBuffer(), readAttachment, new ReadHandler());

            Attachment writeAttachment = new Attachment();
            writeAttachment.setClient(asynchronousSocketChannel);
            writeAttachment.setByteBuffer(ByteBuffer.allocate(2048));

            //Send
            asynchronousSocketChannel.write(writeAttachment.getByteBuffer(), writeAttachment, new WriteHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        log.info("Failed to accept a connection.");
        exc.printStackTrace();
    }
}
