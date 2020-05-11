package project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import project.models.VerificationToken;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class TokenRepositoryJdbcImpl implements TokenRepository {
    private final String table_name = "verification_token";
    //language=SQL
    private final String FIND_TOKEN = "select * from " + table_name + " where id = ?";
    //language=SQL
    private final String FIND_BY_TOKEN = "select * from " + table_name + " where token = ?";
    //language=SQL
    private final String FIND_ALL_TOKENS = "select * from " + table_name;
    //language=SQL
    private final String TOKEN_INSERT = "insert into " + table_name + "(user_id, expiry_date, token) values (?,?,?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<VerificationToken> tokenRowMapper = (row, rowNumber) ->
        VerificationToken.builder()
                .id(row.getLong("id"))
                .user_id(row.getLong("user_id"))
                .expiryDate(row.getDate("expiry_date"))
                .token(row.getString("token"))
                .build();

    @Override
    public Optional<VerificationToken> find(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_TOKEN, new Object[]{id}, tokenRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_TOKEN, new Object[]{token}, tokenRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<VerificationToken> findAll() {
        return jdbcTemplate.query(FIND_ALL_TOKENS, tokenRowMapper);
    }

    @Override
    public Long save(VerificationToken model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(TOKEN_INSERT, new String[]{"id"});
            preparedStatement.setLong(1, model.getUser_id());
            preparedStatement.setDate(2, model.getExpiryDate());
            preparedStatement.setString(3, model.getToken());
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
