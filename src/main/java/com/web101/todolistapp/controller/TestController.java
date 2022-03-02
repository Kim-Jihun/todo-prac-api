package com.web101.todolistapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.tools.javac.comp.Todo;
import com.sun.xml.bind.v2.TODO;
import com.web101.todolistapp.dto.TodoDTO;
import com.web101.todolistapp.model.TodoEntity;
import com.web101.todolistapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web101.todolistapp.dto.ResponseDTO;
import com.web101.todolistapp.dto.TestRequsetBodyDTO;

@RestController
@RequestMapping("todo")
public class TestController {

	@Autowired
	private TodoService service;

	@GetMapping("/{id}")
	public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
		return "Hello World " + id;
	}
/*
	@GetMapping("/test")
	public ResponseEntity<?> todoTest() {
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/testRequestParam")
	public String testControllerRequeParam(@RequestParam(required = false) int id) {
		return "Hello World 2 " + id;
	}
	
	@GetMapping("/testControllerRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequsetBodyDTO testRequestBodyDto) {
		return testRequestBodyDto.getId() + " " + testRequestBodyDto.getMessage();
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello world! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}

	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello world! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(response);
	}
*/
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId = "temporary-user";
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try{
			String temporaryUserId = "temporary-user";

			TodoEntity entity = TodoDTO.toEntity(dto);

			entity.setId(null);

			entity.setUserId(temporaryUserId);

			List<TodoEntity> entities = service.create(entity);

			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

			return ResponseEntity.ok().body(response);
		}catch (Exception e){
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
		String temporaryUserId = "temporary-user";
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		List<TodoEntity> entites = service.update(entity);
		List<TodoDTO> dtos = entites.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
		try{
			String temporaryUserId = "temporary-user";
			TodoEntity entity = TodoDTO.toEntity(dto);
			entity.setUserId(temporaryUserId);

			List<TodoEntity> entities = service.delete(entity);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);

		}catch (Exception e){
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
