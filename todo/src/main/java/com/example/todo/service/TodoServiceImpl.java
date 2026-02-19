package com.example.todo.service;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponseDTO;
import com.example.todo.entity.Todo;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public TodoServiceImpl(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO request) {

        Todo todo = modelMapper.map(request, Todo.class);

        Todo savedTodo = todoRepository.save(todo);

        return modelMapper.map(savedTodo, TodoResponseDTO.class);
    }

    @Override
    public Page<TodoResponseDTO> getTodos(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Todo> todoPage = todoRepository.findAll(pageable);

        return todoPage.map(todo ->
                modelMapper.map(todo, TodoResponseDTO.class)
        );
    }

    @Override
    public Page<TodoResponseDTO> getTodosByStatus(Boolean completed, int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Todo> todoPage = todoRepository.findByCompleted(completed, pageable);

        return todoPage.map(todo ->
                modelMapper.map(todo, TodoResponseDTO.class)
        );
    }

    @Override
    public TodoResponseDTO getTodoById(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new TodoNotFoundException("Todo not found with id: " + id)
                );

        return modelMapper.map(todo, TodoResponseDTO.class);
    }

    @Override
    public TodoResponseDTO markTodoComplete(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new TodoNotFoundException("Todo not found with id: " + id)
                );

        todo.setCompleted(true);

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoResponseDTO.class);
    }
}
