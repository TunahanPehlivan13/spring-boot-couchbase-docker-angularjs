package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoRepository todoRepository;

    @Test
    public void shouldFindTodoListOfUser() {
        Todo todo1 = new Todo.Builder()
                .build();
        Todo todo2 = new Todo.Builder()
                .build();

        when(todoRepository.findByUserId("userId")).thenReturn(Arrays.asList(
                todo1,
                todo2
        ));

        ResponseEntity<Iterable<Todo>> responseEntity = todoController.findTodoListOfUser("userId");

        List<Todo> todoList = Lists.newArrayList(responseEntity.getBody());

        assertThat(todoList, hasItem(todo1));
        assertThat(todoList, hasItem(todo2));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldSaveTodo() {
        Todo todo = new Todo();

        ResponseEntity<Todo> responseEntity = todoController.save(todo);

        verify(todoRepository).save(todo);

        assertNotNull(todo.getId());
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(responseEntity.getBody(), equalTo(todo));
    }
}