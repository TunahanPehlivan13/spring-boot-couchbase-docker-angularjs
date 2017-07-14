package com.todo.repository;

import com.todo.entity.Todo;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

@ViewIndexed(designDoc = "todo", viewName = "all")
public interface TodoRepository extends CouchbaseRepository<Todo, String> {

    @View
    List<Todo> findByUserId(String userId);
}
