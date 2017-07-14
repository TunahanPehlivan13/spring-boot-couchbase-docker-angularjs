package com.todo.entity;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Document
@Data
@ToString
public class User {

    @Id
    private String id;

    @Field
    @NotNull
    private String name;

    @Field
    private String surname;

    @Field
    @NotNull
    private String mail;

    public static class Builder {

        private String name;
        private String surname;
        private String mail;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder mail(String mail) {
            this.mail = mail;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName(name);
            user.setSurname(surname);
            user.setMail(mail);
            return user;
        }
    }
}
