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
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findUsers() {
        log.info("/users is requested");
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody User user) {
        log.info("/save is requested with user : " + user);
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId) {
        log.info("/getUser is requested with userId : " + userId);
        final Optional<User> user = Optional.ofNullable(userRepository.findOne(userId));
        return user.orElseThrow(() -> new UserNotFoundException(userId));
    }
}
