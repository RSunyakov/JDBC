package ru.javalab.socketsapp.programs;

import ru.javalab.socketsapp.repositories.CrudCartRepositoryImpl;
import ru.javalab.socketsapp.repositories.CrudMessagesRepositoryImpl;
import ru.javalab.socketsapp.repositories.CrudProductRepositoryImpl;
import ru.javalab.socketsapp.repositories.CrudRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DbConnection {
    private List<String> properties;


    private CrudRepositoryImpl crudRepository;
    private CrudMessagesRepositoryImpl crudMessagesRepository;
    private CrudCartRepositoryImpl crudCartRepository;
    private CrudProductRepositoryImpl crudProductRepository;

    public DbConnection(List<String> properties) {
        this.properties = properties;
    }

    public CrudRepositoryImpl getCrudRepository() {
        return crudRepository;
    }

    private Connection getConnection(List<String> properties) {
        String url = properties.get(properties.size() - 1);
        Properties props = new Properties();
        props.setProperty("user", properties.get(0));
        props.setProperty("password", properties.get(1));
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return conn;
    }

    public CrudRepositoryImpl createCrudRepository() {
        this.crudRepository = new CrudRepositoryImpl(this.getConnection(properties));
        return crudRepository;
    }

    public CrudMessagesRepositoryImpl createCrudMessagesRepository() {
        this.crudMessagesRepository = new CrudMessagesRepositoryImpl(this.getConnection(properties));
        return crudMessagesRepository;
    }

    public CrudProductRepositoryImpl createCrudProductRepository() {
        this.crudProductRepository = new CrudProductRepositoryImpl(this.getConnection(properties));
        return crudProductRepository;
    }

    public CrudCartRepositoryImpl createCrudCartRepository() {
        this.crudCartRepository = new CrudCartRepositoryImpl(this.getConnection(properties));
        return crudCartRepository;
    }

    public CrudMessagesRepositoryImpl getCrudMessagesRepository() {
        return crudMessagesRepository;
    }

    public CrudCartRepositoryImpl getCrudCartRepository() {
        return crudCartRepository;
    }

    public CrudProductRepositoryImpl getCrudProductRepository() {
        return crudProductRepository;
    }
}
