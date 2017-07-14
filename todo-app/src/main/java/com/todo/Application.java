package com.todo;

import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.entity.enums.Priority;
import com.todo.repository.TodoRepository;
import com.todo.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        this.userRepository.deleteAll();
        User user = saveUser();
        User user1 = saveUser1();

        Todo todo = new Todo();
        todo.setId(UUID.randomUUID().toString());
        todo.setNote("dsda");
        todo.setPriority(Priority.HIGH);
        todo.setUserId(user.getId());
        todoRepository.save(todo);
    }

    private User saveUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("Tunahan");
        user.setSurname("Pehlivan");
        user.setMail("tunahanpehlivan13@gmail.com");
        return this.userRepository.save(user);
    }

    private User saveUser1() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("Tunahan1");
        user.setSurname("Pehlivan");
        user.setMail("tunahanpehlivan13@gmail.com1");
        return this.userRepository.save(user);
    }
}
