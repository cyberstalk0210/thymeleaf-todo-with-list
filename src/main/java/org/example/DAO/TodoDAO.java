package org.example.DAO;

import lombok.RequiredArgsConstructor;
import org.example.model.Todo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TodoDAO {
    private final JdbcTemplate jdbcTemplate;

    // Insert a new Todo
    public void create(Todo todo) {
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(LocalDateTime.now());
        }

        var sql = "INSERT INTO todos(title, priority,created_at) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql);
    }

    public List<Todo> getAll() {
        var sql = "SELECT * FROM todos;";
        var mapper = BeanPropertyRowMapper.newInstance(Todo.class);

//        RowMapper<Todo> todoRowMapper = (rs, rowNum) -> {
//            Todo todo = new Todo();
//            todo.setId(rs.getInt("id"));
//            todo.setTitle(rs.getString("title"));
//            todo.setPriority(Priority.valueOf(rs.getString("priority")));  // Enumni to'g'ri o'qing
//            todo.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
//            return todo;
//        };
        return jdbcTemplate.query(sql,mapper);
    }

    public void delete(int id) {
        var sql = "DELETE FROM todos WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public void updateById(Todo todo) {
        var sql = "UPDATE todos SET title = ?, priority = ?, created_at = ? WHERE id = ?;";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getPriority(), todo.getCreatedAt(), todo.getId());
    }
}
