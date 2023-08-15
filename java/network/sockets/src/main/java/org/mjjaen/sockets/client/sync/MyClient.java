package org.mjjaen.sockets.client.sync;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
public class MyClient implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private UUID id;
    private ExecutorService fixedThreadPool;

    public MyClient(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.id = UUID.randomUUID();
            this.fixedThreadPool = createThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    public UUID getId() {
        return this.id;
    }

    public void closeEverything() {
        try {
            if (this.socket != null)
                this.socket.close();
            if (this.bufferedReader != null)
                this.bufferedReader.close();
            if (this.bufferedWriter != null)
                this.bufferedWriter.close();
            if (this.fixedThreadPool != null) {
                this.fixedThreadPool.shutdown();
                this.fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS); // or what ever
                this.fixedThreadPool.shutdownNow();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startClient() {
        fixedThreadPool.submit(this); //This one executes the run method, which starts to listen for messages
        initializeSender(this.getId().toString());
    }

    private void initializeSender(String message) {
        try {
            this.bufferedWriter.write(message);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected() && ! socket.isClosed()) {
                message = scanner.nextLine();
                this.bufferedWriter.write(message);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
                if(message.compareTo("bye") == 0) {
                    closeEverything();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    private void listenForMessages() {
        String message;
        while (socket.isConnected() && ! socket.isClosed()) {
            try {
                message = bufferedReader.readLine();
                if(message != null)
                    log.info("Read message: " + message);
            } catch (IOException e) {
                closeEverything();
            }
        }
    }

    private ExecutorService createThreadPool () {
        return Executors.newFixedThreadPool(1);
    }

    @Override
    public void run() {
        listenForMessages();
    }
}
