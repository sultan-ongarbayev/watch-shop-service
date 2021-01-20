Watch shop service
==================
Implementation of watch shop service which is used to store items for sale.

Technologies used
------------------
* Java - programming language, version 15
* [Maven](https://maven.apache.org) - widely used build system for Java-based projects
* [Spring](https://spring.io) - set of frameworks (IoC/Dependency Injection, Spring MVC, Spring Transaction)
* [Lombok](https://projectlombok.org) - popular framework used to automatically generate repetitive Java code (constructors, getters/setters,
  etc.)
* [Logback](http://logback.qos.ch) - logging framework
* [MySQL](https://www.mysql.com) - SQL database management system

Getting started
----------------
In order to build service you need to clone the project to local machine:
```shell
git clone https://github.com/sultan-ongarbayev/watch-shop-service.git
```
After that you can change your directory to the project root:
```shell
cd watch-shop-service
```
and build program with Maven:
```shell
mvn package
```
It will generate `WAR` package which can be deployed to application server.

Deployment
----------
`WAR` package itself is useless until deployed on application server. Any application server can be used, but for
purposes of this project Tomcat was chosen. It's lightweight and easy to install and administrate, comparing to other
enterprise products such as JBoss or GlassFish. Since service uses database connection to store data, it's necessary to
configure Tomcat datasource `jdbc/WatchShopDatabase`. This type of configuration helps to separate database
administration from application code. It makes easier to change or swap database connection, also it's safer to store
database credentials on server insted of code.
### Tomcat DataSource configuration example
#### server.xml
```xml
<Resource name="jdbc/WatchShopDatabase"
          global="jdbc/WatchShopDatabase"
          auth="Container"
          type="javax.sql.DataSource"
          driverClassName="com.mysql.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/WATCH_SHOP_DB"
          username="WATCH_SHOP_USER"
          password="***************"
          maxActive="10"
          maxIdle="5"
          minIdle="2"
          initialSize="2"
          maxWait="10000"/>
```
####context.xml
```xml
<ResourceLink name="jdbc/WatchShopDatabase"
              global="jdbc/WatchShopDatabase"
              auth="Container"
              type="javax.sql.DataSource"/>
```
