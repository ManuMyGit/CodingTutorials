package org.mjjaen.sockets.client.async;

import lombok.*;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

@NoArgsConstructor
@Getter
@Setter
public class Attachment {
    private AsynchronousSocketChannel client;
    private ByteBuffer byteBuffer;
    private Scanner scanner;
    private String uuid;
}
