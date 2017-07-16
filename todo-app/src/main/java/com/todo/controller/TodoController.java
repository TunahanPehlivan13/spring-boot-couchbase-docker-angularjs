package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.exception.UserNotFoundException;
import com.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
@Slf4j
public class TodoController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Todo>> findTodoListOfUser(@PathVariable(required = true) String userId) {
        log.info("/todoListOfUser is requested with userId : " + userId);
        final Optional<User> optional = Optional.ofNullable(userRepository.findOne(userId));
        User user = optional.orElseThrow(() -> new UserNotFoundException(userId));
        return ResponseEntity.ok(user.getTodoList());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Todo> put(@Valid @RequestBody Todo todo, @PathVariable(required = true) String userId) {
        log.info("/save is requested with todo : " + todo + ", userId : " + userId);
        final Optional<User> optional = Optional.ofNullable(userRepository.findOne(userId));
        User user = optional.orElseThrow(() -> new UserNotFoundException(userId));
        user.getTodoList().add(todo);
        userRepository.save(user);
        return new ResponseEntity(todo, HttpStatus.CREATED);
    }
}
