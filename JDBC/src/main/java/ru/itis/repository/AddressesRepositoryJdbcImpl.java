package ru.itis.repository;

import ru.itis.models.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressesRepositoryJdbcImpl implements AddressesRepository {
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from addresses where id = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_OWNER_ID ="select * from addresses where owner_id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from addresses";
    //language=SQL
    private static final String SQL_INSERT = "insert into addresses(address, owner_id) values (?, ?)";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from addresses where id = ?";

    private Connection connection;

    private RowMapper<Address> addressRowMapper = row ->
            Address.builder()
            .id(row.getLong("id"))
            .address(row.getString("address"))
                    .ownerId(row.getLong("owner_id"))
            .build();

    public AddressesRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Address> find(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Optional<Address> addressResult = Optional.empty();
            if (result.next()) {
                Address address = addressRowMapper.mapRow(result);
                addressResult = Optional.of(address);
            }
            result.close();
            statement.close();
            return addressResult;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Address> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet result = statement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (result.next()) {
                Address address = addressRowMapper.mapRow(result);
                addresses.add(address);
            }
            result.close();
            statement.close();
            return addresses;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(Address address) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getAddress());
            statement.setLong(2, address.getOwnerId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating address failed");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                address.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating address failed");
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

    @Override
    public List<Address> findByOwnerId(Long ownerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_OWNER_ID);
            statement.setLong(1, ownerId);
            ResultSet result = statement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (result.next()) {
                Address address = addressRowMapper.mapRow(result);
                addresses.add(address);
            }
            result.close();
            statement.close();
            return addresses;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}


