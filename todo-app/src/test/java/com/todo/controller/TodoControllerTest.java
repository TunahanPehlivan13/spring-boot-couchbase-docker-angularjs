package com.todo.controller;

import com.todo.Application;
import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.entity.enums.Priority;
import com.todo.repository.TodoRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TodoControllerTest extends BaseControllerTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        super.setup();
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnCreatedWhenAddingNewTodo() throws Exception {
        Todo todo = new Todo.Builder()
                .note("note")
                .priority(Priority.HIGH)
                .userId("userId")
                .build();

        mockMvc.perform(post("/todo").content(this.json(todo)).contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.note", equalTo("note")))
                .andExpect(jsonPath("$.priority", equalTo("HIGH")))
                .andExpect(jsonPath("$.userId", equalTo("userId")));
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewTodoIfNoteIsNull() throws Exception {
        Todo todo = new Todo.Builder()
                .priority(Priority.HIGH)
                .userId("userId")
                .build();

        mockMvc.perform(post("/todo").content(this.json(todo)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewTodoIfPriorityIsNull() throws Exception {
        Todo todo = new Todo.Builder()
                .note("note")
                .userId("userId")
                .build();

        mockMvc.perform(post("/todo").content(this.json(todo)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewTodoIfUserIdIsNull() throws Exception {
        Todo todo = new Todo.Builder()
                .note("note")
                .priority(Priority.HIGH)
                .build();

        mockMvc.perform(post("/todo").content(this.json(todo)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOkWhenGettingTodoListOfUser() throws Exception {
        User user = new User.Builder()
                .mail("test@mail.com")
                .name("name")
                .surname("surname")
                .build();

        userRepository.save(user);

        Todo todo1 = new Todo.Builder()
                .note("note1")
                .priority(Priority.HIGH)
                .userId(user.getId())
                .build();

        todoRepository.save(Arrays.asList(todo1));

        mockMvc.perform(get("/todo").param("userId", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note", equalTo("note1")))
                .andExpect(jsonPath("$[0].priority", equalTo("HIGH")))
                .andExpect(jsonPath("$[0].userId", equalTo(user.getId())));
    }

    @After
    public void destroy() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }
}