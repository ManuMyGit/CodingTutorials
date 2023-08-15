package org.mjjaen.sockets.server.async;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.CompletionHandler;

@Slf4j
public class WriteHandler implements CompletionHandler<Integer, Attachment> {
    @Override
    public void completed(Integer result, Attachment attachment) {

    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {

    }
}
