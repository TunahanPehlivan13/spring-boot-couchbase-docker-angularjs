package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@Slf4j
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findTodoListOfUser(@RequestParam String userId) {
        log.info("/todoListOfUser is requested with userId : " + userId);
        return ResponseEntity.ok(todoRepository.findByUserId(userId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody Todo todo) {
        log.info("/save is requested with todo : " + todo);
        todo.setId(UUID.randomUUID().toString());
        todoRepository.save(todo);
        return new ResponseEntity(todo, HttpStatus.CREATED);
    }
}
