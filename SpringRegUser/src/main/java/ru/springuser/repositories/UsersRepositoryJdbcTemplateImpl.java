package ru.springuser.repositories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.springuser.model.Role;
import ru.springuser.model.State;
import ru.springuser.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from users where id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from users";
    //language=SQL
    private static final String SQL_INSERT = "insert into users (email, code,  password, country, gender, birthday, state, role) values (?, ?, ?, ?, ?, ?, ?, ?)";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from users where id = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_CODE = "select * from users where code = ?";
    //language=SQL
    private static final String SQL_UPDATE_CODE = "update users set code = ? where id = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .activationCode(row.getString("code"))
            .hashPassword(row.getString("password"))
            .gender(row.getString("gender"))
            .country(row.getString("country"))
            .birthday(row.getString("birthday"))
            .state(State.valueOf(row.getString("state")))
            .role(Role.valueOf(row.getString("role")))
            .build();

    @Override
    public Optional<User> find(Long id) {
        try {
            User user =  jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user =  jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new Object[]{email}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByCode(String code) {
        try {
            User user =  jdbcTemplate.queryForObject(SQL_SELECT_BY_CODE, new Object[]{code}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getActivationCode());
            statement.setString(3, entity.getHashPassword());
            statement.setString(4, entity.getCountry());
            statement.setString(5, entity.getGender());
            statement.setString(6, entity.getBirthday());
            statement.setString(7, entity.getState().toString());
            statement.setString(8, entity.getRole().toString());
            return statement;
        }, keyHolder);

        entity.setId((Long)keyHolder.getKey());
    }


    @Override
    public void updateCode(Long id, String code) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_UPDATE_CODE);
            statement.setString(1, code);
            statement.setLong(2, id);
            return statement;
        });
        /*jdbcTemplate.update(SQL_UPDATE_CODE, new Object[]{id, code}, new int[]{Types.BIGINT, Types.VARCHAR});*/
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, new Object[]{id}, new int[]{Types.INTEGER});
    }
}
