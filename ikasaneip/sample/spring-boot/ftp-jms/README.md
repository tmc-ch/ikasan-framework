# sample-spring-boot-ftp-jms

Sample spring-boot-ftp-jms project provides self contained example of Ikasan integration module. 
The sample is build as fat-jar containing all dependencies and bootstraps as a spring-boot web application with embedded tomcat web-container. 
As majority of core ikasan services depend on persistent store this sample starts up with embedded in memory H2 database.

sample-spring-boot-ftp-jms provides example of integration module using FTP and JMS which is standard EIP approach. The module contains two flow:
* Ftp To Jms Flow (This flow downloads file from FTP server and converts it to Map Message and sends it to private JMS queue)
  * Ftp Consumer -  Connects to SFTP server to download file
  * Ftp Payload to Map Converter
  * Ftp Jms Producer - Standard JMS Producer
* Jms To Ftp Flow (This flow consumes JMS Map message from private queue converts it to Payload object and sends it of to SFTP server.)
  * Ftp Jms Consumer - Standard JMS Consumer
  * MapMessage to FTP Payload Converter - Converts Map Message to Payload Object
  * Ftp Producer - Delivers the file to FTP server

## How to build from source

```
mvn clean install
```


## How to startup

If you managed to obtain the jar by building it or by downloading the relevant version from the public mvn repo:
* https://repo1.maven.org/maven2/org/ikasan/sample-spring-boot-ftp-jms/

You can start up the sample 

```java -jar sample-spring-boot-ftp-jms-2.0.0-SNAPSHOT.jar```

If all went well you will see following 
```
2017-10-22 20:42:55.896  INFO 2837 --- [           main] o.i.m.s.ModuleInitialisationServiceImpl  : Module host [localhost:8080] running with PID [2837]
2017-10-22 20:42:55.907  INFO 2837 --- [           main] o.i.m.s.ModuleInitialisationServiceImpl  : Server instance  [Server [id=null, name=localhost, description=http://localhost:8080//sample-boot-ftp-jms, url=http://localhost, port=8080, createdDateTime=Sun Oct 22 20:42:55 BST 2017, updatedDateTime=Sun Oct 22 20:42:55 BST 2017]], creating...
(...)

2017-10-22 20:11:10.628  INFO 2734 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-10-22 20:11:10.640  INFO 2734 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 0
2017-10-22 20:11:10.788  INFO 2734 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-10-22 20:11:10.798  INFO 2734 --- [           main] o.i.s.s.boot.builderpattern.Application  : Started Application in 11.208 seconds (JVM running for 11.712)
Context ready
```

You can now access the basic web interface http://localhost:8080/sample-boot-ftp-jms/ 


## How to navigate the web console


* Open Login Page ![Login](../../../developer/docs/sample-images/sample-login.png) 

* Login using admin/admin as username and password ![Home](../../../developer/docs/sample-images/home-page.png) 
