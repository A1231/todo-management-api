package com.todos.todos.service;

import com.todos.todos.request.TodoRequest;
import com.todos.todos.request.TodoResponse;

import java.util.List;

public interface TodoService {

    TodoResponse createTodo(TodoRequest todoRequest);
    List<TodoResponse> getAllTodos();
    TodoResponse toggleTodoCompletion(long id);
    void deleteTodo(long id);
}
