package com.example.todo.service;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponseDTO;
import com.example.todo.entity.Todo;

import org.springframework.data.domain.Page;

public interface TodoService {

    TodoResponseDTO createTodo(TodoRequestDTO request);

    Page<TodoResponseDTO> getTodos(int page, int size);

    Page<TodoResponseDTO> getTodosByStatus(Boolean completed, int page, int size);

    TodoResponseDTO getTodoById(Long id);

    TodoResponseDTO markTodoComplete(Long id);
}
