package org.example.Service;

import org.example.Model.Todo;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TodoServiceImpl implements TodoService {
    List<Todo> todos = new ArrayList<>();
    private Integer counter = 1;

    @Override
    public List<Todo> getAllTodos() {
        return todos;
    }

    @Override
    public Todo findById(Integer id) {
        for (Todo t : todos) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        throw new RuntimeException("Bunday ID lik todo yo'q");
    }

    @Override
    public void addTodo(Todo todo) {
        todo.setId(counter++);
        todos.add(todo);
    }

    @Override
    public void updateTodo(Todo updatedTodo) {
        for (Todo t : todos) {
                t.setTitle(updatedTodo.getTitle());
                t.setPriority(updatedTodo.getPriority());
                t.setCreatedAt(updatedTodo.getCreatedAt());
        }
    }

    @Override
    public Todo getById(Integer id) {
        return todos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo topilmadi"));
    }

    @Override
    public void deleteTodo(Integer id) {
        for (Todo t : todos) {
            if (t.getId().equals(id)) {
                todos.remove(t);
                return;
            }
        }
        throw new RuntimeException("Bunday ID lik todo yo'q");
    }
}
