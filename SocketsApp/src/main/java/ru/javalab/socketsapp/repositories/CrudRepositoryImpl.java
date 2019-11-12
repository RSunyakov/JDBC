package ru.javalab.socketsapp.repositories;

import ru.javalab.socketsapp.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CrudRepositoryImpl implements CrudRepository<User> {
    private Connection connection;

    public CrudRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<User>userRowMapper = rs ->
            new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
            );

    @Override
    public void save(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO chat_user(username, password) VALUES (" + "'" + username + "', '" + password + "')");
            user.setId(this.getUserId(username, password));
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void update(User user) {

    }

    @Override
    public Optional<User> find(Integer id) {
        User user = null;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM chat_user WHERE id =" + id);
            if (rs.next()) {
                user = userRowMapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(user) ;
    }

    @Override
    public void delete(User user) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM chat_user WHERE id = " + user.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT * FROM chat_user";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(userRowMapper.mapRow(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void saveMessage(User user, String message) {
        Integer id = user.getId();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO chat_log(id, timedate, message) VALUES (" + id + ", '" + new Date().toString() + "', '" + message + "')");
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Integer getUserId(String username, String password) {
        Integer id = null;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id FROM chat_user WHERE username='" + username + "' AND password='" + password + "'");
            rs.next();
          id = rs.getInt("id");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return id;
    }

    public boolean checkUser(String username, String password) {
        boolean flag = false;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM chat_user WHERE username='" + username + "' AND password='" + password + "'");
            if (rs.next()) {
                flag = true;
            } else flag = false;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return flag;
    }

    public boolean checkUserName(String username) {
        boolean flag = false;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT username FROM chat_user WHERE username='" + username + "'");
            if (rs.next()) {
                flag = true;
            } else flag = false;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return flag;
    }
}
