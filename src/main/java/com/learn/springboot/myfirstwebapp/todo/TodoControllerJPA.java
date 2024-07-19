package com.learn.springboot.myfirstwebapp.todo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Controller
@SessionAttributes("username")
public class TodoControllerJPA {
	private TodoServiceJPA_Impl todoServiceJPA;

	public TodoControllerJPA(TodoServiceJPA_Impl todoService) {
		this.todoServiceJPA = todoService;
	}

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername(model);
		List<Todo> todoList = todoServiceJPA.getAllTodos(username);
		// model.put("todoList", todoList);
		model.addAttribute("todoList", todoList);
		return "listTodosWithCss";
	}


	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String showNewTodoPage(ModelMap model ) {
		String username = getLoggedInUsername(model);
		Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}

		String username = getLoggedInUsername(model);
		todo.setUsername(username);
		todoServiceJPA.addTodo(todo);

		return "redirect:list-todos";
	}

	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		// Delete todo

		todoServiceJPA.deleteByIdTodo(id);
		return "redirect:list-todos";

	}

	@RequestMapping(value = "update-todo", method = RequestMethod.GET)
	public String showUpdatePage(@RequestParam int id, ModelMap model) {
		Todo todo = todoServiceJPA.getByIdTodo(id);
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
		todoServiceJPA.updateByIdTodo(todo);
		return "redirect:list-todos";
	}
	private String getLoggedInUsername(ModelMap model) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
