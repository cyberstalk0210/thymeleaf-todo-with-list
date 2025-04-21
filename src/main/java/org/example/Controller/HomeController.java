package org.example.Controller;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.example.Model.Todo;
import org.example.Service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TodoService todoService;

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("todos", todoService.getAllTodos());
        modelAndView.addObject("todo", new Todo());
        return modelAndView;
    }

    @PostMapping("todos/add")
    public ModelAndView addTodos(@ModelAttribute Todo newTodo) {
        todoService.addTodo(newTodo);
        return new ModelAndView("redirect:/home");
    }

    @PostMapping("todos/edit")
    public ModelAndView updateTodo(@ModelAttribute Todo newTodo) {
        todoService.updateTodo(newTodo);
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/todos/edit/{id}")
    public String editTodoPage(@PathVariable("id") Integer id, Model model) {
        Todo todo = todoService.getById(id);
        model.addAttribute("todo", todo);
        return "edit";
    }
    @PostMapping("todos/delete/{id}")
    public String deleteTodo(@PathVariable("id")Integer id){
        todoService.deleteTodo(id);
        return "redirect:/home";
    }

}
