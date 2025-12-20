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
        <li><a href="#basic-installation">Basic infrastructure (dockers)</a></li>
        <ul>
           <li><a href="#executing-microservices-locally ">Executing microservices locally</a></li>
        </ul>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- About this project -->
## About this project

This is the solution project for the **SA PRAC2**.

In this file you will find how to locate and execute, in two different ways, all or some of the microservices. Executables can be found at photofilm4-acfcfolder

This solution contains; 

* A docker-compose.yml file to startup the basic infrastructure needed to run the services
* A folder for the ProductCatalog microservice 
* A folder for the User microservice 
* A folder for the Notification microservice 
* A folder for the Course microservice. **This folder has the implemented classes for the PRAC2**
* A folder photofilm4u-acfc for 
    * The **executables jar files**
    * An executable **create-containers** script to push and run all the solution, including microservices, inside Docker.
    

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

**All this ports should be available**

## Project folder structure

```
autumn-2025
├ README.md
├ docker-compose.yml
├ autumn-2025-notification
├ autumn-2025-productcatalog
├ autumn-2025-user
├ autumn-2025-course
└ photofilm4u-acfc
```

## Installation
This project can be installed and executed in two different ways. You can run it safely and completely inside Docker, that means that all required dependencies and microservices will run on Docker, or you can run each microservice separately, using Docker to run only kafka and postgreSQL.

Ports and basic configuration has not been changed and it is as it has been delivered initially.

### Microservices Docker installation

To execute all the project, that means, kafka, postgres, adminer and all microservices in docker, you should navigate to photofilm4u-acfc and execute from the work folder
* Windows
```
create-containers.bat
```
* Linux
```
bash create-containers.sh
```

or 
```
chmod +x create-containers.sh
./create-containers.sh
```
_IMPORTANT:_ Linux version of the script has not been test it, **use it carefully.**

<p align="right">(<a href="#top">go up</a>)</p>


### Basic installation 
If you want just the default containers and execute the generated java -jar for each service separatily, you should:

* In case basic Docker container structure does not exist
  * go to root project folder and execute 
  ```
  docker compose up
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
#### Executing microservices locally  
* Once basic containers had been created and running you can execute each microservice locally

  * Open a terminal for each microservice you want to execute and go to **photofilm4u-acfc** folder and execute
    * **java -jar course-0.0.1.jar**
    * **java -jar notification-0.0.1.jar**
    * **java -jar productcatalog-0.0.1.jar**
    * **java -jar user-0.0.1.jar**      

You should see each service running on its port.



<p align="right">(<a href="#top">go up</a>)</p>

# Contact
A.César Flores Carrera
afloresca@uoc.edu