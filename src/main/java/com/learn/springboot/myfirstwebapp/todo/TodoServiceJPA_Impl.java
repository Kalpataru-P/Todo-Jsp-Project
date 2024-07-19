package com.learn.springboot.myfirstwebapp.todo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.learn.springboot.myfirstwebapp.model.Todo;
@Service
@SessionAttributes("username")
public class TodoServiceJPA_Impl implements TodoServiceJPA {
	
	TodoRepository repository;

	public TodoServiceJPA_Impl(TodoRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<Todo> getAllTodos(String username) {

		return repository.findByUsername(username);
	}

	@Override
	public Todo addTodo(Todo todo) {
		Todo newTodo = repository.save(todo);
		return newTodo;
	}

	@Override
	public Todo getByIdTodo(Integer id) {
		Todo todo = repository.findById(id).get();
		return todo;
	}

	@Override
	public Todo updateByIdTodo(Todo todo) {
		Todo updateTodo = repository.save(todo);
		return updateTodo;
	}

	@Override
	public void deleteByIdTodo(Integer id) {
		Todo todo = repository.findById(id).get();
		repository.delete(todo);

	}



}
