package com.example.todo.controller;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponseDTO;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Create Todo
    @PostMapping
    public TodoResponseDTO createTodo(
            @Valid @RequestBody TodoRequestDTO request
    ) {
        return todoService.createTodo(request);
    }

    // Get All Todos (Pagination + Sorting)
    @GetMapping
    public Page<TodoResponseDTO> getTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Boolean completed
    ) {

        if (completed != null) {
            return todoService.getTodosByStatus(completed, page, size);
        }

        return todoService.getTodos(page, size);
    }

    // Get Todo by ID
    @GetMapping("/{id}")
    public TodoResponseDTO getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    // Mark Complete
    @PatchMapping("/{id}/complete")
    public TodoResponseDTO markComplete(@PathVariable Long id) {
        return todoService.markTodoComplete(id);
    }
}
