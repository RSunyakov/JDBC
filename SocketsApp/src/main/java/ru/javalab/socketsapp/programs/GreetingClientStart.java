package ru.javalab.socketsapp.programs;

import ru.javalab.socketsapp.clients.GreetingClient;

public class GreetingClientStart {
    public static void main(String[] args) {
        GreetingClient client = new GreetingClient();
        client.startConnection("127.0.0.1", 6000);
    }
}
