package org.mjjaen.sockets;

import org.mjjaen.sockets.client.sync.MyClientInitializer;
import org.mjjaen.sockets.client.async.MyAsyncClientInitializer;
import org.mjjaen.sockets.server.async.MyAsyncServerInitializer;
import org.mjjaen.sockets.server.sync.MyServerInitializer;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        if(args.length != 1)
            System.exit(-1);
        if(args[0].compareToIgnoreCase("server") == 0) {
            MyServerInitializer.initializeAndRunServer();
        } else if(args[0].compareToIgnoreCase("client") == 0) {
            MyClientInitializer.initializeAndRunClient();
        } else if(args[0].compareToIgnoreCase("asyncserver") == 0) {
            MyAsyncServerInitializer.initializeAndRunServer();
        } else if(args[0].compareToIgnoreCase("asyncclient") == 0) {
            MyAsyncClientInitializer.initializeAndRunClient();
        }else
            System.exit(-1);
    }
}
