package ru.javalab.socketsapp.servers;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.javalab.socketsapp.models.User;
import ru.javalab.socketsapp.programs.DbConnection;
import ru.javalab.socketsapp.repositories.CrudRepositoryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MultiClientServer {
    private ServerSocket serverSocket;
    private DbConnection dbConnection;
    private List<ClientHandler> clients;

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public MultiClientServer() {
        clients = new ArrayList<ClientHandler>();
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while(true) {
            try {
                ClientHandler handler = new ClientHandler(serverSocket.accept());
                handler.start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void logoutUser(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }


    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private User user;
        private int countUser;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            clients.add(this);
            System.out.println("New client!");
        }

        @Override
        public void run() {
            try {
               for (ClientHandler client : clients) {

                    PrintWriter writer = new PrintWriter(client.clientSocket.getOutputStream(), true);
                    Scanner sc = new Scanner(new InputStreamReader(client.clientSocket.getInputStream()));
                    writer.println("Введите имя пользователя: ");
                    String login = sc.nextLine();
                    writer.println("Введите пароль пользователя: ");
                    String password = sc.nextLine();
                    this.login(login, password);

                }
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (ClientHandler client : clients) {
                        PrintWriter writer = new PrintWriter(client.clientSocket.getOutputStream(), true);
                        writer.println(line);
                        dbConnection.getCrudRepository().saveMessage(user, line);
                    }
                }
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public void login(String username, String password) {
            this.user = new User(null, username, password);
            CrudRepositoryImpl crudRepository = dbConnection.createCrudRepository();
            crudRepository.save(user);
        }
    }
}
