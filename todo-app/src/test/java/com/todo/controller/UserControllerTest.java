package com.todo.controller;

import com.todo.entity.User;
import com.todo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {


    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldFindUsers() {
        User user1 = new User.Builder()
                .build();
        User user2 = new User.Builder()
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(
                user1,
                user2
        ));

        ResponseEntity<Iterable<User>> responseEntity = userController.findUsers();

        assertThat(responseEntity.getBody(), hasItem(user1));
        assertThat(responseEntity.getBody(), hasItem(user2));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldSaveUser() {
        User user = new User();

        ResponseEntity<User> responseEntity = userController.save(user);

        verify(userRepository).save(user);

        assertNotNull(user.getId());
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(responseEntity.getBody(), equalTo(user));
    }
}