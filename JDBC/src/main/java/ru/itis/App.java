package ru.itis;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import ru.itis.models.Address;
import ru.itis.models.User;
import ru.itis.repository.AddressesRepository;
import ru.itis.repository.AddressesRepositoryJdbcImpl;
import ru.itis.repository.UsersRepository;
import ru.itis.repository.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class App 
{
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/jdbc";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "SRr729038";

    public static void main( String[] args ) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(connection);
        AddressesRepository addressesRepository = new AddressesRepositoryJdbcImpl(connection);
        List<User> users = usersRepository.findAll();
        List<Address> addresses = addressesRepository.findAll();
        addressesRepository.delete(3L);
        System.out.println(addresses);
        System.out.println(usersRepository.find(1L));
        System.out.println(users);
    }
}
