#!/usr/bin/env bash
mvn clean package -DskipTests

docker build -t tunahanpehlivan/todo-app .
docker run -p 8080:8080 -t tunahanpehlivan/todo-app