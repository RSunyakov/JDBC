package project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import project.models.File;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component(value = "fileRepositoryJdbc")
public class FileRepositoryJdbcImpl implements FileRepository {
    private final String table_name = "files_table";
    //language=SQL
    private final String FIND_FILE = "select * from " + table_name + " where id = ?";
    //language=SQL
    private final String FIND_ALL_FILES = "select * from " + table_name;
    //language=SQL
    private final String USER_INSERT = "insert into " + table_name + "(name,db_name,size,type,url) values (?,?,?,?,?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<File> fileRowMapper = (row, rowNumber) ->
        File.builder()
                .id(row.getLong("id"))
                .name(row.getString("name"))
                .db_Name(row.getString("db_name"))
                .size(row.getLong("size"))
                .type(row.getString("type"))
                .url(row.getString("url"))
                .build();

    @Override
    public Optional<File> find(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_FILE, new Object[]{id}, fileRowMapper));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<File> findAll() {
        return jdbcTemplate.query(FIND_ALL_FILES, fileRowMapper);
    }

    @Override
    public Long save(File model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(USER_INSERT, new String[]{"id"});
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getDb_Name());
            preparedStatement.setLong(3, model.getSize());
            preparedStatement.setString(4, model.getType());
            preparedStatement.setString(5, model.getUrl());
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
