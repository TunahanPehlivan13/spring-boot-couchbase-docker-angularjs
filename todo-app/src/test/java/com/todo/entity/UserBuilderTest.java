package com.todo.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserBuilderTest {

    @Test
    public void shouldBuildUser() {
        User user = new User.Builder()
                .mail("mail")
                .name("name")
                .surname("surname")
                .build();

        assertNotNull(user.getId());
        assertEquals(user.getMail(), "mail");
        assertEquals(user.getName(), "name");
        assertEquals(user.getSurname(), "surname");
    }
}