package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.DAO.TodoDAO;
import org.example.model.Todo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoDAO todoDAO;

    @GetMapping("/")
    public String goHome() {
        return "/home";
    }

    @GetMapping("/home")
    public Model home(Model model) {
        List<Todo> todos = todoDAO.getAll();
        model.addAttribute("todos", todos);
        return model;
    }

    @GetMapping("/todos/add")
    public String getAddTodosPage(Model model) {
        model.addAttribute("todo", new Todo());
        return "addTodo";
    }

    @GetMapping("/todos/edit/{id}")
    public String getEditTodosPage(@PathVariable("id") int id, Model model) {
        Todo todo = todoDAO.getById(id);
        model.addAttribute("todo", todo);
        return "edit";
    }

    @PostMapping("/todos/add")
    public String addTodos(@ModelAttribute Todo todos) {
        todoDAO.create(todos);
        return "redirect:/home";
    }

    @PostMapping("/todos/edit/{id}")
    public String editTodos(@ModelAttribute Todo todos) {
        todoDAO.updateById(todos);
        return "redirect:/home";
    }

    @PostMapping("/todos/delete/{id}")
    public String deleteTodos(@PathVariable("id") int id) {
        todoDAO.delete(id);
        return "redirect:/home";
    }

}
