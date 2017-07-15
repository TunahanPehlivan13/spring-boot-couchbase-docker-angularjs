FROM couchbase:community
MAINTAINER Tunahan Pehlivan <tunahanpehlivan13@gmail.com>

RUN chmod +x /entrypoint.sh
COPY configure-node.sh /opt/couchbase
RUN chmod +x /opt/couchbase/configure-node.sh

CMD ["/opt/couchbase/configure-node.sh"]
ENTRYPOINT ["/entrypoint.sh"] CMD ["couchbase-server"] 