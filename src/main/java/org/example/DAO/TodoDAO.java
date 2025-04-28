package org.example.DAO;

import lombok.RequiredArgsConstructor;
import org.example.Enum.Priority;
import org.example.model.Todo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TodoDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(Todo todo) {
        String sql = "INSERT INTO todos (title, priority, created_at) VALUES (:title, :priority, :created_at)";
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority().toString())
                .addValue("created_at", todo.getCreated_at());
        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public void updateById(Todo todo){
        var sql = "UPDATE todos SET title= :title, priority = :priority, created_at = :created_at WHERE id = :id";
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id",todo.getId())
                    .addValue("title",todo.getTitle())
                        .addValue("priority",todo.getPriority().toString())
                            .addValue("created_at",todo.getCreated_at());

        namedParameterJdbcTemplate.update(sql, paramSource);
    }
    public void delete(int id){
        String sql = "DELETE FROM todos WHERE id = :id";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        namedParameterJdbcTemplate.update(sql,paramSource);
    }
    public Todo getById(int id){
        String sql = "SELECT * FROM todos WHERE id = :id";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, paramSource,getRowMapper());
}
    public List<Todo> getAll(){
        String sql = "SELECT * FROM todos";

       return namedParameterJdbcTemplate.query(sql,new MapSqlParameterSource() ,getRowMapper());
    }

    private RowMapper<Todo> getRowMapper(){
        return (rs, rowNum) -> {
            Todo todo = new Todo();
            todo.setId(rs.getInt("id"));
            todo.setTitle(rs.getString("title"));
            todo.setPriority(Priority.valueOf(rs.getString("priority")));
            todo.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            return todo;
        };
    }

}