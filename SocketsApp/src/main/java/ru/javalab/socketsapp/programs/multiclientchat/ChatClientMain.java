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
    }

    public static String argsParse(String s) {
        int index = s.indexOf("=");
        return s.substring(index + 1);
    }
}
