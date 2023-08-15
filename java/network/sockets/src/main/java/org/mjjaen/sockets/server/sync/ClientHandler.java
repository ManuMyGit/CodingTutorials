package org.mjjaen.sockets.server.sync;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Getter
public class ClientHandler implements Runnable {
    public static List<ClientHandler> clientsConnected = new ArrayList();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String id;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.id = bufferedReader.readLine();
            clientsConnected.add(this);
            log.info("ClientHandler assigned to the client with id = " + this.id);
            broadcastMessage("Client with id = " + this.id + " just joined.");
        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        try {
            this.bufferedWriter.write(new Date().toString());
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            listenForMessages();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    public void broadcastMessage(String message) {
        for(ClientHandler clientHandler : clientsConnected) {
            if(clientHandler.getId().compareTo(this.id) != 0) {
                try {
                    clientHandler.getBufferedWriter().write(message);
                    clientHandler.getBufferedWriter().newLine();
                    clientHandler.getBufferedWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    closeEverything();
                }
            }
        }
    }

    public void listenForMessages() {
        log.info("Listening for messages for the client with id = " + this.id);
        String message;
        while (socket.isConnected() && ! socket.isClosed()) {
            try {
                message = bufferedReader.readLine();
                if(message != null) {
                    log.info("Message " + message + " received from client with id = " + this.id);
                    if(message.compareTo("bye") != 0)
                        broadcastMessage(this.id + " said: " + message);
                    else {
                        clientsConnected.remove(this);
                        broadcastMessage("Client with id = " + this.id + " left the chat.");
                    }
                }
            } catch (IOException e) {
                closeEverything();
            }
        }
    }

    public void closeEverything() {
        try {
            if(this.socket != null)
                this.socket.close();
            if(this.bufferedReader != null)
                this.bufferedReader.close();
            if(this.bufferedWriter != null)
                this.bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
