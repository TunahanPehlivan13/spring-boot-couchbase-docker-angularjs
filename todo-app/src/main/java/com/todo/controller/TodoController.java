package com.todo.controller;

import com.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
