Use to build assembly and docker image

```
sbt package
```

or build with tests

```
sbt test package
```

---
See `docker-compose.yml`

```
docker-compose up
```

Zookeeper UI

```
http://localhost:8080/exhibitor/v1/ui/index.html
```

---
To upload logs to elk use `docker-compose-elk.yml` and `docker-compose-syslog.yml`.

To write logs to logstash via syslog elk stack should be started first.
Syslog url is not resolved via docker dns:  https://github.com/docker/docker/issues/20370


```
GET /_cat/indices?v
```
---
Useful flags

```
-admin.port=:0 -http.port=:0
```



