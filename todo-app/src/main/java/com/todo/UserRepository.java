package com.todo;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@ViewIndexed(designDoc = "user", viewName = "all")
public interface UserRepository extends CouchbaseRepository<User, String> {

}
