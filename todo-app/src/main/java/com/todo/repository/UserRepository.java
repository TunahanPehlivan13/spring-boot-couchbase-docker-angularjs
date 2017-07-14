package com.todo.repository;

import com.todo.entity.User;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@ViewIndexed(designDoc = "user", viewName = "all")
public interface UserRepository extends CouchbaseRepository<User, String> {

}
