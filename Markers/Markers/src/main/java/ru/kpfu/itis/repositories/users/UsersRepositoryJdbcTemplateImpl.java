package ru.kpfu.itis.repositories.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.models.Role;
import ru.kpfu.itis.models.State;
import ru.kpfu.itis.models.User;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from users where id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from users";
    //language=SQL
    private static final String SQL_INSERT = "insert into users (email, code,  password, country, gender, birthday, state, role) values (?, ?, ?, ?, ?, ?, ?, ?)";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from users where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getLong("id"))
                    .email(row.getString("email"))
                    .hashPassword(row.getString("hashPassword"))
                    .gender(row.getString("gender"))
                    .firstName(row.getString("firstName"))
                    .lastName(row.getString("lastName"))
                    .typeOfStudent(row.getString("typeOfStudent"))
                    .state(State.valueOf(row.getString("state")))
                    .role(Role.valueOf(row.getString("role")))
                    .build();

    @Override
    public Optional<User> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }
}
