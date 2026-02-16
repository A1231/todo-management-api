package com.todos.todos.controller;

import com.todos.todos.request.TodoRequest;
import com.todos.todos.request.TodoResponse;
import com.todos.todos.service.TodoService;
import com.todos.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Todo REST API Endpoints", description = "Operations for managing user todos")
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create todo for the user", description=" Create todo for the user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo (@Valid @RequestBody TodoRequest todoRequest){
        return todoService.createTodo(todoRequest);
    }

    @Operation(summary = "Get all Todos for the user", description = "fetch all todos from signed in user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos(){
        return todoService.getAllTodos();
    }

    @Operation(summary = "Update Todos for the user", description = "Update the todo for the signed in user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) long id){
        return todoService.toggleTodoCompletion(id);
    }

    @Operation(summary = "Delete Todo for the user", description = "Delete the todo for the signed in user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable @Min(1) long id){
         todoService.deleteTodo(id);
    }


}
