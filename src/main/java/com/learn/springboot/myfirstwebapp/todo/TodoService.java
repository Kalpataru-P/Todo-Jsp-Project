package com.learn.springboot.myfirstwebapp.todo;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.learn.springboot.myfirstwebapp.model.Todo;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//@Service
@SessionAttributes("username")
public class TodoService {

        public static List<Todo> todos = new ArrayList<>();
        public static int todoCount = 0;
        static {
                todos.add(new Todo(++todoCount, "Kalpataru", "Learn Front End Development 1", LocalDate.now(), false));
                todos.add(new Todo(++todoCount, "Kalpataru", "Learn AWS 1",LocalDate.now().plusYears(1), false));
                todos.add(new Todo(++todoCount, "Kalpataru", "Learn DevOps 1",LocalDate.now().plusYears(2), false));
                todos.add(new Todo(++todoCount, "Kalpataru", "Learn Full Stack Development 1",
                															LocalDate.now().plusYears(3), false));
        }

        public List<Todo> findByUserName(String username) {
                Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);

                return todos;
        }

        public void addTodo(String username, String description, LocalDate targetDate, Boolean done) {
                Todo todo = new Todo(++todoCount, username, description, targetDate, done);
                todos.add(todo);
        }

        public void deleteById(int id) {
                // todo.getId() == id
                // todo -> todo.getId() == id
                Predicate<? super Todo> predicate = todo -> todo.getId() == id;
                todos.removeIf(predicate);
        }

        public Todo findById(int id) {
                // todo.getId() == id
                // todo -> todo.getId() == id
                Predicate<? super Todo> predicate = todo -> todo.getId() == id;
                Todo todo = todos.stream().filter(predicate).findFirst().get();
                return todo;
        }

        public void updateById(@Valid Todo todo) {

                deleteById(todo.getId());
                todos.add(todo);

        }
}
