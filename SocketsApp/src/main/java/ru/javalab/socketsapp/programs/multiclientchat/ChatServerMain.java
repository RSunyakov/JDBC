package ru.javalab.socketsapp.programs.multiclientchat;

import ru.javalab.socketsapp.programs.DbConnection;
import ru.javalab.socketsapp.servers.MultiClientServer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServerMain {
    public static void main(String[] args) {
        args = new String[2];
        args[0] = "--port=6000";
        args[1] = "--db-properties-path=C:\\JavaLab\\SocketsApp\\src\\main\\resources\\db.properties";
        int port = Integer.parseInt(argsParse(args[0]));
        String dbPropertiesPath = argsParse(args[1]);
        DbConnection dbConnection = new DbConnection(dbPropertiesParse(dbPropertiesPath));
        MultiClientServer multiClientServer = new MultiClientServer();
        multiClientServer.setDbConnection(dbConnection);
        multiClientServer.start(port);
    }
    public static String argsParse(String s) {
        int index = s.indexOf("=");
        return s.substring(index + 1);
    }

    public static List<String> dbPropertiesParse(String path) {
        List<String> properties = new ArrayList<>();
        File dbProperties = new File(path);
        try {
            Scanner sc = new Scanner(dbProperties);
            while(sc.hasNextLine()) {
                String proper = sc.nextLine();
                int index = proper.indexOf(" ");
                properties.add(proper.substring(index + 1));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return properties;
    }
}
