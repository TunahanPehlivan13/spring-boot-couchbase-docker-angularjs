package com.todo;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.view.DefaultView;
import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.View;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        CouchbaseCluster cluster = CouchbaseCluster.create("localhost");
        Bucket bucket = cluster.openBucket("default");
        BucketManager bucketManager = bucket.bucketManager();

        View view = DefaultView.create("byUserId", "function (doc, meta) {  if(doc._class == \"com.todo.entity.Todo\" && doc.userId) {    emit(doc.userId, null);  }}");

        DesignDocument designDocument = bucketManager.getDesignDocument("todo");
        designDocument.views().add(view);
        bucketManager.upsertDesignDocument(designDocument);
    }
}
