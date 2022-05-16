## Used software and versions
Java 		: Version 11              <br/>
Spring Boot 	: Version 2.6.7     <br/>
Maven                             <br/>
Restful API                       <br/>
Rabbit MQ                         <br/>
JPA                               <br/>
Hibernate                         <br/>
H2 (In memory database)           <br/>
JUnit5 		: Jupiter 5.8.2         <br/>

## How to start
Application.java --> Run As --> Java Application

## Databases
Separate databases are implemeted for each microservice.                  <br/>

http://localhost:9000/h2-console/login.jsp       	comodo-login-service    <br/>
http://localhost:9001/h2-console/login.jsp       	comodo-group-service    <br/>
http://localhost:9002/h2-console/login.jsp   	comodo-todo-service          <br/>

## General structure
A separate port definition is made in the application.properties file for each microservice.             <br/>
Rabbit mq address is specified in the application.properties file for each microservice.                 <br/>
The records read from the data.sql file are automatically loaded into the database when the application starts.

## Rabbit MQ Messaging
There are two message flows. Thanks to this flows, defining a group belonging to a non-existent user or defining a todo for a non-existent group is prevented.

**First message flow**
When	: A user is added or updated via comodo-login-service    <br/>
From	: comodo-login-service UserController.java               <br/>
To 	: comodo-group-service MessageListener.java                <br/>
Result	: Added or updated user information is also recorded to comodo-group-service database

**Second message flow**
When	: A group is added/updated/deleted via comodo-group-service <br/>
From	: comodo-group-service TodoGroupController.java             <br/>
To 	: comodo-todo-service MessageListener.java                    <br/>
Result	: Added/updated/deleted group information is also recorded to comodo-todo-service database







