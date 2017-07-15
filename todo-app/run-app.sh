#!/usr/bin/env bash
mvn clean package -DskipTests

docker build -t todo/app .
docker run -p 8080:8080 -t todo/app