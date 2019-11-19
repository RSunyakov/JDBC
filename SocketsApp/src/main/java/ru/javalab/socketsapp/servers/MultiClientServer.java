package ru.javalab.socketsapp.servers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.javalab.socketsapp.models.*;
import ru.javalab.socketsapp.models.command.LoginCommand;
import ru.javalab.socketsapp.models.command.MessageCommand;
import ru.javalab.socketsapp.models.command.PaginationCommand;
import ru.javalab.socketsapp.programs.DbConnection;
import ru.javalab.socketsapp.repositories.CrudMessagesRepositoryImpl;
import ru.javalab.socketsapp.repositories.CrudRepositoryImpl;
import ru.javalab.socketsapp.utils.HashPassword;

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
    private CrudRepositoryImpl crudRepository;
    private CrudMessagesRepositoryImpl crudMessagesRepository;
    private List<ClientHandler> clients;

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
       crudRepository = this.dbConnection.createCrudRepository();
       crudMessagesRepository = this.dbConnection.createCrudMessagesRepository();
    }

    public MultiClientServer() {
        clients = new ArrayList<ClientHandler>();
    }

    public  List<ClientHandler> getClients() {
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
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public  void logoutUser(ClientHandler clientHandler) {
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
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                    while ((line = reader.readLine()) != null) {
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        ObjectMapper mapper = new ObjectMapper();
                            Request request = mapper.readValue(line, Request.class);
                            switch (request.getHeader()) {
                                case "login":
                                    Request<LoginCommand> login = mapper.readValue(line, new TypeReference<Request<LoginCommand>>() {
                                    });
                                    Optional<User> user = crudRepository.find(login.getPayload().getName());
                                    if (user.isPresent()) {
                                        if (HashPassword.checkPassword(login.getPayload().getPassword(), user.get().getPassword())) {
                                            this.user = user.get();
                                            getClients().add(this);
                                            writer.println("Добро пожаловать в чат!");
                                        } else {
                                            writer.println("Неверный логин или пароль");
                                        }
                                    } else {
                                        this.user = new User(null, login.getPayload().getName(), login.getPayload().getPassword());
                                        crudRepository.save(this.user);
                                        getClients().add(this);
                                        writer.println("Вы вошли в чат!");
                                    }
                                    break;
                                case "logout":
                                    logoutUser(this);
                                    break;
                                case "message":
                                    Request<MessageCommand> message = mapper.readValue(line, new TypeReference<Request<MessageCommand>>() {
                                    });
                                    for (ClientHandler client : getClients()) {
                                        if (!client.equals(this)) {
                                            writer = new PrintWriter(client.clientSocket.getOutputStream(), true);
                                            writer.println(client.user.getUsername() + ": " + message.getPayload().getMessage());
                                        }
                                    }
                                    crudMessagesRepository.save(new Message(0, message.getPayload().getMessage(), this.user.getId(), null));
                                    break;
                                case "get-messages":
                                    Request<PaginationCommand> pagination = mapper.readValue(line, new TypeReference<Request<PaginationCommand>>() {
                                    });
                                    Optional<List<Message>> messageList = crudMessagesRepository.find(pagination.getPayload().getPage());
                                    if (messageList.isPresent()) {
                                        for (int i = 0; i < messageList.get().size(); i++) {
                                            writer.print("id: " + messageList.get().get(i).getId() + " message: " + messageList.get().get(i).getMessage() + " userId: " + messageList.get().get(i).getUserId() + " date: " + messageList.get().get(i).getDate().toString());
                                            writer.println("");
                                        }
                                       /* writer.println(getJson(new PaginationResponse(messageList.get())));*/
                                    } else {
                                        writer.println("");
                                    }
                                    break;
                            }
                        }
                    reader.close();
                    clientSocket.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        private String getJson(Object o) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
