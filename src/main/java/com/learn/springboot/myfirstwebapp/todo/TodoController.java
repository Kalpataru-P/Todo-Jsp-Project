package com.learn.springboot.myfirstwebapp.todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.learn.springboot.myfirstwebapp.model.Todo;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

//@Controller
@SessionAttributes("username")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // @RequestMapping("list-todos")
    // public String listAllTodos(ModelMap model){
    // List<Todo> todoList = todoService.findByUserName("Kalpataru");
    // model.put("todoList",todoList);
    // return "listTodos";
    // }

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String username = (String) model.get("username");
        List<Todo> todoList = todoService.findByUserName(username);
        // model.put("todoList", todoList);
        model.addAttribute("todoList", todoList);
        return "listTodosWithCss";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String username = (String) model.get("username");
        Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodoPage(ModelMap model, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }

        String username = (String) model.get("username");
        todoService.addTodo(username, todo.getDescription(),
                todo.getTargetDate(), false);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
        // Delete todo

        todoService.deleteById(id);
        return "redirect:list-todos";

    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String showUpdatePage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "todo";

    }

    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String updateSingleTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }

        String username = (String) model.get("username");
        todo.setUsername(username);
        todoService.updateById(todo);
        return "redirect:list-todos";
    }
}
