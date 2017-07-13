package com.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        this.userRepository.deleteAll();
        User user = saveUser();
        System.out.println(this.userRepository.findOne(user.getId()));
    }

    private User saveUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName("tunahan1");
        user.setLastName("Smith");
        return this.userRepository.save(user);
    }
}
