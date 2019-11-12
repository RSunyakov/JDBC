package ru.javalab.socketsapp.servers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GreetingServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String s = reader.readLine();
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            if (s.equals("Hello")) {
                writer.println("Hi!");
            } else {
                System.out.println("Unrecognized greeting");
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
