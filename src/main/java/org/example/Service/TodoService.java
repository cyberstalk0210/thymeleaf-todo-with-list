package org.example.Service;

import org.example.Model.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {
    List<Todo> getAllTodos();
    Todo findById(Integer id);
    void addTodo(Todo todo);
    void updateTodo(Todo todo);
    void deleteTodo(Integer id);
    Todo getById(Integer id);
}
