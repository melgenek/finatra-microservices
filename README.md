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

---
Zookeeper UI

```
http://localhost:8080/exhibitor/v1/ui/index.html
```

---
To get Zipkin trace:

```
curl -H "Host: event" http://localhost:4140/events/event1
```

Zipkin location:

```
http://localhost:9411/
```
---
Kibana takes some time to start! Located here: 
```
http://localhost:5601
```

Check elastic log index

```
GET /_cat/indices?v
GET /logstash-2017.03.28/_search
GET /logstash-2017.03.28
DELETE  /logstash-2017.03.28
```
---
Useful flags

```
-admin.port=:0 -http.port=:0
```
