package org.example.DAO;

import lombok.RequiredArgsConstructor;
import org.example.model.Todo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class TodoDAO {
    private final JdbcTemplate jdbcTemplate;

    // Insert a new Todo
    public void create(Todo todo) {
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(LocalDateTime.now());
        }

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("todos")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("priority", todo.getPriority().toString());
        parameters.put("created_at", todo.getCreatedAt());

        Number id = insert.executeAndReturnKey(parameters);
        todo.setId(id.intValue()); // Bazadan olingan id ni Todo obyektiga o'rnatish
    }

    public List<Todo> getAll() {
        var sql = "SELECT * FROM todos;";
        var mapper = BeanPropertyRowMapper.newInstance(Todo.class);
        return jdbcTemplate.query(sql, mapper);
    }

    public void delete(int id) {
        var sql = "DELETE FROM todos WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public void updateById(Todo todo) {
        var sql = "UPDATE todos SET title = ?, priority = ?, created_at = ? WHERE id = ?;";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getPriority().toString(), todo.getCreatedAt(), todo.getId());
    }

    public Todo getById(int id) {
        var sql = "SELECT * FROM todos WHERE id = ?";
        var mapper = BeanPropertyRowMapper.newInstance(Todo.class);
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

}
