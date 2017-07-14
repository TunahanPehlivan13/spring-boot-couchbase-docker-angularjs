package com.todo.entity;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;

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
}
