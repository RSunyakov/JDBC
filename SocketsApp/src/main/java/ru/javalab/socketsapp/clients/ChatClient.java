package ru.javalab.socketsapp.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.javalab.socketsapp.models.Message;
import ru.javalab.socketsapp.models.Product;
import ru.javalab.socketsapp.models.Request;
import ru.javalab.socketsapp.models.Response;
import ru.javalab.socketsapp.models.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


public class ChatClient {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean isFinished;
    private String token;
    private String command;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(() -> {
                ObjectMapper mapper = new ObjectMapper();
                while(!isFinished) {
                    try {
                        String line = reader.readLine();
                        if (command.equals("/login")) {
                            Response<String> auth = mapper.readValue(line,
                                    new TypeReference<Response<String>>() {
                                    });
                            if (auth.getData() != null) {
                                System.out.println("Я тут");
                                token = auth.getData();
                                System.out.println("Я получил токен");
                                System.out.println("Вы вошли в чат!");
                                command = "";
                                startMessage();
                            } else {
                                System.out.println("Неверный пароль");
                                auth();
                            }
                        } else if (command.equals("/message/get")) {
                            Response<List<Message>> messages = mapper.readValue(line, new TypeReference<Response<List<Message>>>() {
                            });
                            for (int i = 0; i < messages.getData().size(); i++) {
                                System.out.println(messages.getData().get(i).getUsername() + ": " + messages.getData().get(i).getMessage());
                            }
                           /* messages.getData().stream().forEach(message -> System.out.println(message.getUsername() + ": " + message.getMessage()));*/
                            command = "";
                        } else if (command.equals("/product/get")) {
                            Response<List<Product>> products = mapper.readValue(line, new TypeReference<Response<List<Product>>>() {
                            });
                            products.getData().stream().forEach(product -> System.out.println("Id: " + product.getId() + ", Name: " + product.getName() + ", Price:" + product.getPrice()));
                            command = "";
                        } else {
                            System.out.println(line);
                            break;
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }).start();
            auth();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Runnable receiveMessagesTask = new Runnable() {
        public void run() {
            while (true) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

    public void sendRequest(String json) {
        writer.println(json);
        writer.flush();
    }

    private void startMessage() {
        Scanner sc = new Scanner(System.in);
        new Thread(() -> {
            while (!isFinished) {
                String text = sc.nextLine();
                if (text.equals("/logout")) {
                    Request logoutRequest = new Request<>("/logout", null);
                    sendRequest(getJson(logoutRequest));
                    isFinished = true;
                } else if (text.contains("/message/get")) {
                    command = "/message/get";
                    int page = Integer.parseInt(text.substring(text.lastIndexOf("t") + 2, text.length()));
                    Request<PaginationCommand> paginationRequest = new Request<>("/message/get", new PaginationCommand(page));
                    sendRequest(getJson(paginationRequest));
                } else if (text.contains("/product/get")) {
                    command = "/product/get";
                    int page = Integer.parseInt(text.substring(text.lastIndexOf("t") + 2, text.length()));
                    Request<PaginationCommand> paginationRequest = new Request<>("/product/get",
                            new PaginationCommand(page));
                    sendRequest(getJson(paginationRequest));
                } else if (text.equals("/product/add")) {
                    System.out.println("Наименование товара: ");
                    String name = sc.nextLine();
                    System.out.println("Цена: ");
                    String price = sc.next();
                    Request<AddProductCommand> addProductRequest = new Request<>("/product/add", new AddProductCommand(price, name));
                    addProductRequest.setToken(token);
                    sendRequest(getJson(addProductRequest));
                } else if (text.equals("/product/delete")) {
                    System.out.println("id Товара: ");
                    String id = sc.nextLine();
                    Request<DeleteProductCommand> deleteProductRequest = new Request<>("/product/delete", new DeleteProductCommand(Integer.valueOf(id)));
                    deleteProductRequest.setToken(token);
                    sendRequest(getJson(deleteProductRequest));
                } else if (text.equals("/cart/add")) {
                    System.out.println("id Товара: ");
                    String id = sc.nextLine();
                    Request<AddCartCommand> addCartRequest = new Request<>("/cart/add", new AddCartCommand(Integer.valueOf(id)));
                    addCartRequest.setToken(token);
                    sendRequest(getJson(addCartRequest));
                } else {
                    Request<MessageCommand> messageRequest = new Request<>("/message/send", new MessageCommand(text));
                    sendRequest(getJson(messageRequest));
                }
            }
        }).start();
    }

    private void auth() {
        Scanner sc = new Scanner(System.in);
        command = "/login";
        System.out.println("Для авторизации, введите комманду /login");
        if (sc.next().equals("/login")) {
            System.out.println("Введите имя пользователя: ");
            String username = sc.next();
            System.out.println("Введите пароль: ");
            String password = sc.next();
            Request<LoginCommand> loginRequest = new Request<>("/login", new LoginCommand(username, password));
            sendRequest(getJson(loginRequest));
        }
    }

    private static String getJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
