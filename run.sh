#!/usr/bin/env bash
cd couchbase
sh run-couchbase.sh

sleep 15

cd ..
cd todo-app
mvn clean package -DskipTests

cd target
java -jar todo-app-1.0.0.jar
