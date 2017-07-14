package com.todo.entity;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.todo.entity.enums.Priority;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@Data
public class Todo {

    @Id
    private String id;

    @Field
    @NotNull
    private String note;

    @Field
    @NotNull
    private Priority priority;

    @Field
    @NotNull
    private String userId;
}
