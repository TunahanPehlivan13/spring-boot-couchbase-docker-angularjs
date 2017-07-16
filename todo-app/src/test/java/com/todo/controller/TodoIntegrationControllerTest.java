package com.todo.controller;

import com.todo.Application;
import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.entity.enums.Priority;
import com.todo.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TodoIntegrationControllerTest extends BaseIntegrationControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        super.setup();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnCreatedWhenAddingNewTodo() throws Exception {
        User user = new User.Builder()
                .name("name")
                .mail("mail")
                .build();

        Todo todo = new Todo.Builder()
                .note("note")
                .priority(Priority.HIGH)
                .build();

        userRepository.save(user);

        mockMvc.perform(put("/todo/" + user.getId()).content(this.json(todo)).contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.note", equalTo("note")))
                .andExpect(jsonPath("$.priority", equalTo("HIGH")));
    }

    @Test
    public void shouldReturnNotFoundWhenAddingNewTodoIfUserNotFound() throws Exception {
        Todo todo = new Todo.Builder()
                .note("note")
                .priority(Priority.HIGH)
                .build();

        mockMvc.perform(put("/todo/userId").content(this.json(todo)).contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewTodoIfNoteIsEmpty() throws Exception {
        User user = new User.Builder()
                .build();

        Todo todo = new Todo.Builder()
                .note("")
                .priority(Priority.HIGH)
                .build();

        mockMvc.perform(put("/todo/" + user.getId()).content(this.json(todo)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewTodoIfPriorityIsNull() throws Exception {
        User user = new User.Builder()
                .build();

        Todo todo = new Todo.Builder()
                .note("note")
                .build();

        mockMvc.perform(put("/todo/" + user.getId()).content(this.json(todo)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOkWhenGettingTodoListOfUser() throws Exception {
        Todo todo1 = new Todo.Builder()
                .note("note1")
                .priority(Priority.HIGH)
                .build();

        Todo todo2 = new Todo.Builder()
                .note("note2")
                .priority(Priority.HIGH)
                .build();

        User user1 = new User.Builder()
                .mail("test1@mail.com")
                .name("name1")
                .surname("surname1")
                .todoList(Arrays.asList(todo1))
                .build();

        User user2 = new User.Builder()
                .mail("test2@mail.com")
                .name("name2")
                .surname("surname2")
                .todoList(Arrays.asList(todo2))
                .build();

        userRepository.save(Arrays.asList(user1, user2));

        mockMvc.perform(get("/todo/" + user1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].note", equalTo("note1")))
                .andExpect(jsonPath("$[0].priority", equalTo("HIGH")));
    }

    @Test
    public void shouldReturnNotFoundWhenGettingTodoListOfUserIfUserNotFound() throws Exception {
          mockMvc.perform(get("/todo/userId"))
                .andExpect(status().isNotFound());
    }

    @After
    public void destroy() {
        userRepository.deleteAll();
    }
}