#!/usr/bin/env bash

docker build -t todo/couchbase .
docker run -d --name couchbase -p 8091-8093:8091-8093 -p 11210:11210 todo/couchbase