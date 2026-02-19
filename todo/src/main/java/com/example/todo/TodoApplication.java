package com.example.todo;

import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Bean
	public org.springframework.boot.CommandLineRunner runner(TodoRepository repository){
		return args -> {
			Todo todo=new Todo("Learn Spring Boot", "Build Todo API Project");
			repository.save(todo);
		};
	}

}
