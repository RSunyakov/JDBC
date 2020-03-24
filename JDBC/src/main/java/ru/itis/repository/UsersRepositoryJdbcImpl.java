package ru.itis.repository;

import ru.itis.models.Address;
import ru.itis.models.User;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.*;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT u.id, a.id, owner_id, name, address FROM users u JOIN addresses a on u.id = a.owner_id where u.id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT u.id, a.id, owner_id, name, address FROM users u JOIN addresses a on u.id = a.owner_id";
    //language=SQL
    private static final String SQL_INSERT = "insert into users(name) values (?)";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from users where id = ?";

    private Connection connection;
    private AddressesRepository addressesRepository;

    private RowMapper<User> usersRowMapper = row ->
            User.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .build();

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
        this.addressesRepository = new AddressesRepositoryJdbcImpl(connection);
    }

    @Override
    public Optional<User> find(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Optional<User> userResult = Optional.empty();
            if (result.next()) {
                User user = usersRowMapper.mapRow(result);
                user.setAddresses(addressesRepository.findByOwnerId(user.getId()));
                userResult = Optional.of(user);
            }
            result.close();
            statement.close();
            return userResult;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet result = statement.executeQuery();
            Map<Long, User> map = new HashMap<>();
            User user = null;
            while (result.next()) {
                Long id = result.getLong("id");
                user = map.get(id);
                if (user == null) {
                    String name = result.getString("name");
                    user = new User(id, name, null);
                }
                List<Address> addresses = user.getAddresses();
                if(addresses == null) {
                    addresses = new ArrayList<>();
                    user.setAddresses(addresses);
                }
                Address address = new Address();
                address.setId(result.getLong("a.id"));
                address.setAddress(result.getString("address"));
                address.setOwnerId(result.getLong("u.id"));
                addresses.add(address);
            }
            result.close();
            statement.close();
            return new ArrayList<User>(map.values());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating users failed");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating users failed");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1, id);
            int affectedRecords = statement.executeUpdate();
            System.out.println("Number of deleted records:- " + affectedRecords);
            statement.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
