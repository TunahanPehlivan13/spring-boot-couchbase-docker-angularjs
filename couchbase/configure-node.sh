#!/usr/bin/env bash
set -m

/entrypoint.sh couchbase-server &

sleep 15

# Setup index and memory quota
curl -v -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=1500 -d indexMemoryQuota=512

# Setup services
curl -v http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2Cn1ql%2Cindex

# Setup credentials
curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username=Administrator -d password=password


#Create buckets
curl -v -X POST -u Administrator:password -d name=default -d ramQuotaMB=200 -d authType=none  -d flushEnabled=1  -d proxyPort=11216 http://localhost:8091/pools/default/buckets

echo "Type: $TYPE"


if [ "$TYPE" = "WORKER" ]; then
  sleep 15

  IP=`hostname -I`

  echo "Auto Rebalance: $AUTO_REBALANCE"
  if [ "$AUTO_REBALANCE" = "true" ]; then
    couchbase-cli rebalance --cluster=$COUCHBASE_MASTER:8091 --user=Administrator --password=password --server-add=$IP --server-add-username=Administrator --server-add-password=password
  else
    couchbase-cli server-add --cluster=$COUCHBASE_MASTER:8091 --user=Administrator --password=password --server-add=$IP --server-add-username=Administrator --server-add-password=password
  fi;
fi;

fg 1