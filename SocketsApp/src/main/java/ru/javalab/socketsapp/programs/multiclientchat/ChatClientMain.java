package ru.javalab.socketsapp.programs.multiclientchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.javalab.socketsapp.clients.ChatClient;
import ru.javalab.socketsapp.models.command.LoginCommand;
import ru.javalab.socketsapp.models.command.MessageCommand;
import ru.javalab.socketsapp.models.command.PaginationCommand;
import ru.javalab.socketsapp.models.Request;

import java.util.Scanner;

public class ChatClientMain {
    public static void main(String[] args) {
        args = new String[2];
        args[0] = "--server-ip=127.0.0.1";
        args[1] = "--server-port=6000";
        ChatClient client = new ChatClient();
        client.startConnection(argsParse(args[0]), Integer.parseInt(argsParse(args[1])));
        Scanner sc = new Scanner(System.in);
        System.out.println("Для авторизации, введите комманду /login");
        if (sc.next().equals("/login")) {
            System.out.println("Введите имя пользователя: ");
            String username = sc.next();
            System.out.println("Введите пароль: ");
            String password = sc.next();
            Request<LoginCommand> loginRequest = new Request<>("login", new LoginCommand(username, password));
            client.sendRequest(getJson(loginRequest));
        }
        boolean isFinish = false;
        while (!isFinish) {
            String text = sc.nextLine();
            if (text.equals("/logout")) {
                Request logoutRequest = new Request<>("logout", null);
                client.sendRequest(getJson(logoutRequest));
                isFinish = true;
            } else if (text.contains("/get-messages")) {
                int page = Integer.parseInt(text.substring(text.lastIndexOf("s") + 2, text.length()));
                Request<PaginationCommand> paginationRequest = new Request<>("get-messages",
                        new PaginationCommand(page));
                client.sendRequest(getJson(paginationRequest));
            } else {
                Request<MessageCommand> messageRequest = new Request<>("message", new MessageCommand(text));
                client.sendRequest(getJson(messageRequest));
            }
        }
    }

    public static String argsParse(String s) {
        int index = s.indexOf("=");
        return s.substring(index + 1);
    }

    public static String getJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
