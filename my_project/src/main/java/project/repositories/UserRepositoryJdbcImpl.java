package project.repositories;

import project.models.Role;
import project.models.State;
import project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJdbcImpl implements UserRepository {
    private final String table_name = "user_table";
    //language=SQL
    private final String FIND_USER = "select * from " + table_name + " where id = ?";
    //language=SQL
    private final String FIND_USER_BY_EMAIL = "select * from " + table_name + " where email = ?";
    //language=SQL
    private final String FIND_ALL_USERS = "select * from " + table_name;
    //language=SQL
    private final String VERIFY = "update " + table_name + " set state = '"+ State.CONFIRMED +"' where id = ?";
    //language=SQL
    private final String USER_INSERT = "insert into " + table_name + "(name,surname,email,password,state,role) values (?,?,?,?,?,?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (row, rowNumber) ->
        User.builder()
                .id(row.getLong("id"))
                .name(row.getString("name"))
                .surname(row.getString("surname"))
                .email(row.getString("email"))
                .password(row.getString("password"))
                .role(Enum.valueOf(Role.class, row.getString("role")))
                .state(Enum.valueOf(State.class,row.getString("state")))
                .build();

    @Override
    public Optional<User> find(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER, new Object[]{id}, userRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void verification(Long id){
        jdbcTemplate.update(VERIFY, new Object[]{id});
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL, new Object[]{email}, userRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public boolean isVerified(Long id) {
        return find(id).get().getState().equals(State.CONFIRMED);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USERS, userRowMapper);
    }

    @Override
    public Long save(User model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(USER_INSERT, new String[]{"id"});
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getSurname());
            preparedStatement.setString(3, model.getEmail());
            preparedStatement.setString(4, model.getPassword());
            preparedStatement.setString(5, model.getState().toString());
            preparedStatement.setString(6, model.getRole().toString());
            return preparedStatement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

}
