package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.exception.UserNotFoundException;
import com.todo.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldFindTodoListOfUser() {
        Todo todo1 = new Todo.Builder()
                .build();
        Todo todo2 = new Todo.Builder()
                .build();

        User user = new User.Builder()
                .todoList(Arrays.asList(todo1, todo2))
                .build();

        when(userRepository.findOne(user.getId())).thenReturn(user);

        ResponseEntity<Iterable<Todo>> responseEntity = todoController.findTodoListOfUser(user.getId());

        List<Todo> todoList = Lists.newArrayList(responseEntity.getBody());

        assertThat(todoList, hasItem(todo1));
        assertThat(todoList, hasItem(todo2));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNoFoundExceptionWhenFindTodoListOfNonExistingUser() {
        when(userRepository.findOne("userId")).thenReturn(null);

        todoController.findTodoListOfUser("userId");
    }

    @Test
    public void shouldPutTodo() {
        Todo todo = new Todo();

        User user = new User.Builder()
                .build();

        when(userRepository.findOne(user.getId())).thenReturn(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        ResponseEntity<Todo> responseEntity = todoController.put(todo, user.getId());

        verify(userRepository).save(captor.capture());

        assertThat(captor.getValue().getTodoList(), hasItem(todo));
        assertThat(responseEntity.getBody(), equalTo(todo));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNoFoundExceptionWhenPutTodoOfNonExistingUser() {
        Todo todo = new Todo();

        when(userRepository.findOne("userId")).thenReturn(null);

        todoController.put(todo, "userId");
    }
}