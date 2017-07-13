package com.todo;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Data
public class User {

    @Id
    private String id;

    @Field
    private String firstName;

    @Field
    private String lastName;
}
