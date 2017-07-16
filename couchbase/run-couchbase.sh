#!/usr/bin/env bash

docker build -t tunahanpehlivan/couchbase .
docker run -d -p 8091-8093:8091-8093 -p 11210:11210 tunahanpehlivan/couchbase