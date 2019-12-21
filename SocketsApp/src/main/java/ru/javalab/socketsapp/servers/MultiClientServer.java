package ru.javalab.socketsapp.servers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.javalab.socketsapp.models.*;
import ru.javalab.socketsapp.models.command.*;
import ru.javalab.socketsapp.programs.DbConnection;
import ru.javalab.socketsapp.repositories.CrudCartRepositoryImpl;
import ru.javalab.socketsapp.repositories.CrudMessagesRepositoryImpl;
import ru.javalab.socketsapp.repositories.CrudProductRepositoryImpl;
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
    private CrudProductRepositoryImpl crudProductRepository;
    private CrudCartRepositoryImpl crudCartRepository;
    private List<ClientHandler> clients;

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
       crudRepository = this.dbConnection.createCrudRepository();
       crudMessagesRepository = this.dbConnection.createCrudMessagesRepository();
       crudCartRepository = this.dbConnection.createCrudCartRepository();
       crudProductRepository = this.dbConnection.createCrudProductRepository();
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
        private PrintWriter writer;
        private User user;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("New client!");
        }
        @Override
        public void run() {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    Request request = mapper.readValue(line, Request.class);
                    switch (request.getHeader()) {
                        case "/login":
                            Request<LoginCommand> login = mapper.readValue(line, new TypeReference<Request<LoginCommand>>() {
                            });
                            login(login);
                            break;
                        case "/logout":
                            logoutUser(this);
                            break;
                        case "/message/send":
                            Request<MessageCommand> message = mapper.readValue(line, new TypeReference<Request<MessageCommand>>() {
                            });
                            messageAdd(message);
                            break;
                        case "/product/add":
                            Request<AddProductCommand> addProductRequest = mapper.readValue(line,
                                    new TypeReference<Request<AddProductCommand>>() {
                                    });
                            productAdd(addProductRequest);
                            break;
                        case "/product/delete":
                            Request<DeleteProductCommand> deleteProductRequest = mapper.readValue(line,
                                    new TypeReference<Request<DeleteProductCommand>>() {
                                    });
                            productDelete(deleteProductRequest);
                            break;
                        case "/message/get":
                            Request<PaginationCommand> pagination = mapper.readValue(line, new TypeReference<Request<PaginationCommand>>() {
                            });
                            messageGet(pagination);
                            break;
                        case "/product/get":
                            Request<PaginationCommand> pagination1 = mapper.readValue(line, new TypeReference<Request<PaginationCommand>>() {
                            });
                            productGet(pagination1);
                            break;
                        case "/cart/add":
                            Request<AddCartCommand> cart = mapper.readValue(line, new TypeReference<Request<AddCartCommand>>() {
                            });
                            addCart(cart);
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
        private void login(Request<LoginCommand> login) throws IOException {
            Optional<User> user = crudRepository.find(login.getPayload().getName());
            if (user.isPresent()) {
                        if (HashPassword.checkPassword(login.getPayload().getPassword(), user.get().getPassword())) {
                            this.user = user.get();
                            getClients().add(this);
                            write(getJson(new Response<>(generateToken(user.get().getRole() == 1))));
                } else {
                    write(getJson(new Response<>(null)));
                }
            } else {
                User u = new User(null, login.getPayload().getName(), login.getPayload().getPassword(), 0);
                this.user = u;
                crudRepository.save(this.user);
                getClients().add(this);
                write(getJson(new Response<>(generateToken(u.getRole() == 1))));
                writer.println("Вы вошли в чат!");
            }
        }

        private void addCart(Request<AddCartCommand> cart) {
            if (cart.getToken() == null) return;
            Algorithm algorithm = Algorithm.HMAC256("secret123");
            try {
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT jwt = verifier.verify(cart.getToken());
                crudCartRepository.save(new Cart(this.user.getId(), cart.getPayload().getProductId()));
            } catch (JWTVerificationException exception) {
                throw new JWTVerificationException(exception.toString());
            }
        }

        private void messageGet(Request<PaginationCommand> pagination) throws IOException {
            Optional<List<Message>> messageList = crudMessagesRepository.find(pagination.getPayload().getPage());
            write(getJson(new Response<>(messageList.get())));
        }

        private void productGet(Request<PaginationCommand> pagination) throws IOException {
            Optional<List<Product>> productList = crudProductRepository.find(pagination.getPayload().getPage());
            write(getJson(new Response<>(productList.get())));
        }

        private void messageAdd(Request<MessageCommand> message) throws IOException {
            for (ClientHandler client : getClients()) {
                    write(client.user.getUsername() + ": " + message.getPayload().getMessage());
            }
            crudMessagesRepository.save(new Message(0, message.getPayload().getMessage(), this.user.getId(), null));
        }

        private void productAdd(Request<AddProductCommand> product) {
            if (product.getToken() == null) return;
            Algorithm algorithm = Algorithm.HMAC256("secret123");
            try {
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT jwt = verifier.verify(product.getToken());
                if (jwt.getClaim("role").asBoolean()) {
                    crudProductRepository.save(new Product(0, product.getPayload().getName(), Float.valueOf(product.getPayload().getPrice())));
                }
            } catch (JWTVerificationException exception) {
            }
        }

        private void productDelete(Request<DeleteProductCommand> product) {
            if (product.getToken() == null) return;
            Algorithm algorithm = Algorithm.HMAC256("secret123");
            try {
                JWTVerifier verifier = JWT.require(algorithm).build();

                DecodedJWT jwt = verifier.verify(product.getToken());
                if (jwt.getClaim("role").asBoolean()) {
                    crudProductRepository.delete(product.getPayload().getId());
                }
            } catch (JWTVerificationException exception) {
            }
        }

        public void write(String response) {
            writer.println(response);
        }

        private String generateToken(boolean isAdmin) {
            Algorithm algorithm = Algorithm.HMAC256("secret123");
            String token = JWT.create()
                    .withSubject(String.valueOf(this.user.getId()))
                    .withClaim("role", isAdmin)
                    .sign(algorithm);
            return token;
        }
    }
}
