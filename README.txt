#*******************************************************************#
## This is the solution project for the SA PRAC2.
#*******************************************************************#

In this file you will find how to locate and execute, in two different ways, all or some of the microservices. 

**** Executables can be found at photofilm4-acfcfolder ****

This solution contains; 

* A docker-compose.yml file to startup the basic infrastructure needed to run the services
* A folder for the ProductCatalog microservice 
* A folder for the User microservice 
* A folder for the Notification microservice 
* A folder for the Course microservice. **This folder has the implemented classes for the PRAC2**
* A folder photofilm4u-acfc for 
    * The **executables jar files**
    * An executable **create-containers** script to push and run all the solution, including microservices, inside Docker.
    

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

autumn-2025
├ README.md
├ docker-compose.yml
├ autumn-2025-notification
├ autumn-2025-productcatalog
├ autumn-2025-user
├ autumn-2025-course
└ photofilm4u-acfc

#*******************************************************************#
### RUNNING MICORSERVICES ON DOCKER
#*******************************************************************#

To execute all the project, that means, kafka, postgres, adminer and all microservices in Docker, 
you should navigate to photofilm4u-acfc and execute, from the work folder, the following command;

* Windows

  create-containers.bat

* Linux
  
  bash create-containers.sh

  or 

  chmod +x create-containers.sh
  ./create-containers.sh

IMPORTANT: Linux version of the script has not been test it, **use it carefully.**

#*******************************************************************#
#### RUNNING MICORSERVICES LOCALLY ####
### BASIC INSTALLATION ###
#*******************************************************************#

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


* Once basic containers had been created and running you can execute each microservice locally

  * Open a terminal for each microservice you want to execute and go to **photofilm4u-acfc** folder and execute
    * **java -jar course-0.0.1.jar**
    * **java -jar notification-0.0.1.jar**
    * **java -jar productcatalog-0.0.1.jar**
    * **java -jar user-0.0.1.jar**      

You should see each service running on its port.


#*******************************************************************#
# Contact
#*******************************************************************#

A.César Flores Carrera
afloresca@uoc.edu