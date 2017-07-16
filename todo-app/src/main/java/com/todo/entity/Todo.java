package com.todo.entity;

import com.todo.entity.enums.Priority;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@ToString
public class Todo {

    @NotEmpty
    private String note;

    @NotNull
    private Priority priority;

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

    public static class Builder {

        private String note;
        private Priority priority;

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Todo build() {
            Todo todo = new Todo();
            todo.setNote(note);
            todo.setPriority(priority);
            return todo;
        }
    }
}
