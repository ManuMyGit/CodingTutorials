package org.mjjaen.sockets.server.sync;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class MyServer {
    private final ServerSocket serverSocket;
    private final ExecutorService cachedThreadPool;

    public MyServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.cachedThreadPool = createThreadPool();
    }

    protected void startServer() {
        try {
            while(!serverSocket.isClosed()) {
                //This is a blocking call
                Socket socket = serverSocket.accept();
                log.info("New client connected: " + socket.getLocalAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(socket);
                cachedThreadPool.submit(clientHandler);
            }
        } catch (IOException ex) {
            log.info("Server exception: " + ex.getMessage());
            closeServer();
        }
    }

    private void closeServer() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
                cachedThreadPool.shutdown();
                cachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
                cachedThreadPool.shutdownNow();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ExecutorService createThreadPool () {
        //return Executors.newCachedThreadPool();
        return new ThreadPoolExecutor(0, 1000,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }
}
