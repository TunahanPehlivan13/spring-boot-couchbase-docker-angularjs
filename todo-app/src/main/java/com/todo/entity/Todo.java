package com.todo.entity;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.todo.entity.enums.Priority;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Document
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class Builder {

        private String note;
        private Priority priority;
        private String userId;

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Todo build() {
            Todo todo = new Todo();
            todo.setId(UUID.randomUUID().toString());
            todo.setNote(note);
            todo.setPriority(priority);
            todo.setUserId(userId);
            return todo;
        }
    }
}
