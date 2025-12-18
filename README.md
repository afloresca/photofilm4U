<div id="top"></div>
<!--
*** Made using the Best-README-Template
*** https://github.com/othneildrew/Best-README-Template/blob/master/README.md
-->


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <p align="center">
   <h2>SA PRAC2 Solution</h2>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Contents</summary>
  <ol>
    <li>
      <a href="#about-this-project">About this project</a>
      <ul>
        <li><a href="#made-with">Made with</a></li>
      </ul>
    </li>
    <li>
      <a href="#before-starting">Before starting</a>
    </li>
    <li>
      <a href="#project-folder-structure">Project Folder Structure</a>
    </li>
    <li>
      <a href="#installation">Tnstallation</a>
      <ul>
        <li><a href="#microservices-docker-installation">Docker Desktop / Docker Compose installation</a></li>
        <li><a href="#basic-infrastructure-dockers">Basic infrastructure (dockers)</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- About this project -->
## About this project

This is the solution project for the **SA PRAC2**.

In this file you will find how to locate and execute, in two different ways, all or some of the microservice. Executables can be found at photofilm4 folder

* A docker-compose.yml file to startup the basic infrastructure needed to run the services
* A folder for the ProductCatalog microservice 
* A folder for the User microservice 
* A folder for the Notification microservice 
* A folder for the Course microservice. **This folder has the implemented classes**
* A folder photofilm4u for 
    * The executables jar files
    * An alternative docker-compose.yml with all the configuration needed to run the whole project, basic infraestructure along with microservices.
    

<p align="right">(<a href="#top">go up</a>)</p>


### Made with

* [Docker](https://www.docker.com/) / [Docker Compose](https://github.com/docker/compose)
* [Spring](https://spring.io/) / [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [Apache Kafka](https://kafka.apache.org/)
* [PostgreSQL](https://www.postgresql.org/)

<p align="right">(<a href="#top">go up</a>)</p>


## Before starting

To set up the containers that are part of the basic infrastructure of the project, the following ports will be used:

* 22181 - Apache Kafka (Zookeeper)
* 19092 - Apache Kafka (Server)
* 54320, 54321, 54322 - PostgreSQL
* 18080 - Adminer
* 18081 - Used by the productcatalog microservice
* 18082 - Used by the user microservice
* 18083 - Used by the notification microservice
* 18084 - Used by the course microservice

To avoid conflicts with other installed applications, the default ports of all applications have been modified. Still, if there is a conflict over a port already in use, simply modifying the ports specified in the [docker-compose.yml](https://github.com/UOC-SA-AUTUMN-2025/autumn-2025/blob/main/docker-compose.yml) file will fix the problem. This link to the official docker compose documentation explains how to modify this configuration using the _ports_: [Networking in Compose](https://docs.docker.com/compose/networking/) option.

__IMPORTANT NOTICE:__ The modified ports will also have to be changed in the microservices configuration (usually defined in the Spring _application.properties_ file).

## Project folder structure

```
autumn-2025
├ README.md
├ docker-compose.yml
├ autumn-2025-notification
├ autumn-2025-productcatalog
├ autumn-2025-user
├ autumn-2025-course
└ photofilm4u
```

## Installation

### Microservices Docker installation

To execute all the project, that means, kafka, postgres, adminer and all microservices in docker, you should navigate to photofilm4u and execute **xxx.bat**
* From the work folder, run the command:

  ```sh
  docker compose up
  (Win)
  ```
  ```sh
  docker-compose up
  (Linux)
  ```


<p align="right">(<a href="#top">go up</a>)</p>


### Basic installation 
It is possible to install on Docker just the default containers and execute on another machine the generated java -jar for each service.

In that case, you should
* In case basic Docker container structure does not exist
  * go to root project folder and execute 
  ```
  docker compose up
  (Win)
  ```
  ```sh
  docker-compose up
  (Linux)
  ```
The following containers should start:

* autumn-2025-adminer_1 - adminer, an SQL client
* autumn-2025-kafka_1 - the kafka server
* autumn-2025-productdb_1 - the postgresql database for the productcatalog service
* autumn-2025-userdb_1 - the postgresql database for the user service
* autumn-2025-coursedb_1 - the postgresql database for the course service
* autumn-2025-zookeeper_1 - kafka zookeeper

In order to verify that all containers are up and running, we will execute the following command:

  ```sh
  docker ps -a
  ```
  
  
* Once basic containers had been created you can execute each service

  * go to photofilm4u folder and execute
    * java -jar course-0.0.1.jar
    * java -jar notification-0.0.1.jar
    * java -jar productcatalog-0.0.1.jar
    * java -jar user-0.0.1.jar       
  



<p align="right">(<a href="#top">go up</a>)</p>

# Contact
A.César Flores Carrera
afloresca@uoc.edu