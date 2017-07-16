package com.todo.controller;

import com.todo.Application;
import com.todo.entity.User;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserIntegrationControllerTest extends BaseIntegrationControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        super.setup();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnOkWhenGettingUsers() throws Exception {
        User user1 = new User.Builder()
                .mail("test1@mail.com")
                .name("name1")
                .surname("surname1")
                .build();

        User user2 = new User.Builder()
                .mail("test2@mail.com")
                .name("name2")
                .surname("surname2")
                .build();

        userRepository.save(Arrays.asList(user1, user2));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string(containsString("name1")))
                .andExpect(content().string(containsString("surname1")))
                .andExpect(content().string(containsString("test1@mail.com")))
                .andExpect(content().string(containsString("name2")))
                .andExpect(content().string(containsString("surname2")))
                .andExpect(content().string(containsString("test2@mail.com")));
    }

    @Test
    public void shouldReturnCreatedWhenSavingNewUser() throws Exception {
        User user = new User.Builder()
                .mail("test@mail.com")
                .name("name")
                .surname("surname")
                .build();

        mockMvc.perform(post("/user").content(this.json(user)).contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.surname", equalTo("surname")))
                .andExpect(jsonPath("$.mail", equalTo("test@mail.com")));
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewUserIfMailIsEmpty() throws Exception {
        User user = new User.Builder()
                .name("name")
                .surname("surname")
                .build();

        mockMvc.perform(post("/user").content(this.json(user)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingNewTodoIfNameIsEmpty() throws Exception {
        User user = new User.Builder()
                .mail("test@mail.com")
                .surname("surname")
                .build();

        mockMvc.perform(post("/user").content(this.json(user)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @After
    public void destroy() {
        userRepository.deleteAll();
    }
}