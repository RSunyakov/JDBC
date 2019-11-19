package ru.javalab.socketsapp.repositories;

import ru.javalab.socketsapp.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrudMessagesRepositoryImpl {
    public static final int PAGE_SIZE = 5;

    private Connection connection;
    private RowMapper<Message> messageRowMapper = rs -> new Message(
            rs.getInt("id"),
            rs.getString("message"),
            rs.getInt("id_user"),
            rs.getDate("date")
    );

    public CrudMessagesRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(Message message) {
        String sqlQuery = "INSERT INTO chat_log(message, id_user) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, message.getMessage());
            stmt.setInt(2, message.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Optional<List<Message>> find(int page) {
        String sqlQuery = "SELECT * FROM chat_log ORDER BY date DESC OFFSET ? LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setInt(1, (page - 1) * PAGE_SIZE);
            stmt.setInt(2, PAGE_SIZE);
            ResultSet rs = stmt.executeQuery();
            List<Message> messagesList = new ArrayList<>();
            while (rs.next()) {
                messagesList.add(messageRowMapper.mapRow(rs));
            }
            return Optional.of(messagesList);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
