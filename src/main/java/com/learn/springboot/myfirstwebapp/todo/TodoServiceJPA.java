package com.learn.springboot.myfirstwebapp.todo;

import java.util.List;

import com.learn.springboot.myfirstwebapp.model.Todo;

public interface TodoServiceJPA {

	List<Todo> getAllTodos(String username);

	Todo addTodo(Todo todo);
	Todo getByIdTodo(Integer id);

	Todo updateByIdTodo(Todo todo);

	void deleteByIdTodo(Integer id);

	
}
