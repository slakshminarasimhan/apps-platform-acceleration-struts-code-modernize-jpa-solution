Migration Evolution

Background

This repository demonstrates a possible evolution for a demonstration architecture of monolith system -> micorservices based on projects I have worked on in the past. 
It assumes that the bounded contexts for the application follow the boundaries outlined. The evolution is based on component based architecture : http://www.appcontinuum.io/ . I'll show in the code how I have broken
the monolith and have performed portfolio analysis and snap analysis.

My objective is to also demonstrate how to replatform and remodernize legacy apps so that properties can be externalized. 
Moreover, how to use NFS or blob-store instead of filesystems, because of epheremal cloud architecture. Mainly, I'll show followings for replatforming:
Spring Bootification, Managing Datasources, Removing Reads from the File System , Removing Persistence to the File System , Logging (ELK or Central logging), Spring Cloud Sleuth, Background Jobs with the Database, 
Background Jobs with AMQP, Remove Instance Specific, Spring Bootification of Struts, Mavenization, and Gradling, EJB and PersistentContext (@EJB annotation)

How to use Spring Cloud Configuration Service with legacy app (Non spring app) and modernize the app towards 12 Factor app.

How to use Spring Boot and leverage the Spring Cloud Service Broker project.

How to use Non-SQL DB (Couchbase) and their health indicators properly while modernizing the legacy apps. I am also a code-committer in spring-projects for this:
https://github.com/spring-projects/spring-boot/pulls/kriskrishna

Above can be applied using any PaaS platform: Cloud Foundry, Mesos (Marathon), AWS ElasticBeanStack, Kubernetes (GoogleCloud)

Evolution tags

Following Tags will be used to show the evolution of the application from one application that leverages components to a distributed system of microservices.


# Monlith Application -> Replatforming -> Remodernize 

See Git tags for step-by-step notes.

```
git tag -ln

v1              First commit
v2              Functional groups
v3              Feature groups (Bounded Context)
v4              Components
v5              Applications
v6              Services
v7              One Application that leverages components
v8              One Non Spring Application that leverages config server for property management
v9              Non Spring App/ Spring App using Minio Server as blob store intead of local file system
v10             One Application that leverages components
v11             Components promoted to a micro-services
v12             One Application that uses non-sql db
v13             Add Eureka service discovery to allow easy service communication
v14             Add Hystrix (Circuit Breakers) for service resiliency
v15             Circuit Breaker Metric Aggregation
v16             Add distributed configuration server
v17             Add asynchronous processing with RabbitMQ
v18             Zuul
```

### Database Setup

```
mysql -uroot --execute="create database uservices_test"

mysql -uroot --execute="grant all on uservices_test.* to 'uservices'@'localhost' identified by 'uservices';"
```

### Schema Migrations

```
flyway -url="jdbc:mysql://localhost:3306/uservices_test?user=root&password=" -locations=filesystem:databases/continuum clean migrate
```

### Test and Production Environment

````
export PORT=8081

export VCAP_SERVICES='{ "services": { "p-mysql": [ { "credentials": { "jdbcUrl": "jdbc:mysql://localhost:3306/uservices_test?user=root&password=&useTimezone=true&serverTimezone=UTC" } } ] } }'

export REGISTRATION_SERVER_ENDPOINT=http://localhost:8883
````

_Note: The registration server endpoint port must match the port used in the FlowTest_