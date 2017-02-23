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
Useful flags

```
-admin.port=:0 -http.port=:0
```



