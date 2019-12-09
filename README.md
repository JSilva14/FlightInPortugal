# FlightInfoApi

Spring Boot API to retrieve information about flight data and average flight prices for all flights provided by TAP and Ryanair between the airports OPO and LIS.
Also contains endpoints to get information about requests made to flight information endpoints. 

## Prerequisites

* Docker Engine (Recommended version 19.03.5):
	* [Docker for Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/)
	* [Docker Desktop for Windows](https://docs.docker.com/docker-for-windows/install/)
	* [Docker Destop for Mac](https://docs.docker.com/docker-for-mac/install/)
* [Docker Compose](https://docs.docker.com/compose/install/) (Recommended version 1.25.0)

#### For local setup
* [Java JDK 13](https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html)
* [Maven 3.6+](https://maven.apache.org/download.cgi)
* A java IDE with Spring support like [Spring Tool Suite](https://spring.io/tools) or [IntelliJ IDEA](https://www.jetbrains.com/idea/)


## Getting Started

Clone the repository into your prefered directory.


## Deployment

#### To execute the docker environment

**In your terminal, navigate to the directory that contains the docker-compose.yaml file and execute the command:**

`docker-compose up` or `docker-compose up -d` for detached mode.

This will setup 2 docker containers, one running the API and one for MongoDB. 

During the API container setup, the code is copied into the container and packaged using **mvn clean install** which will perform all the tests and build an executable jar.
After the jar has been created, the source code is removed from the container and the jar is executed.

* The Api will be exposed on port **8080** of your local machine
* MongoDB will be exposed on port **27017** of your local machine

**IMPORTANT NOTE:** To successfully run the environment using docker, the mongodb connection string in  **src/main/resources/application.properties** must be set to:
´spring.data.mongodb.uri=mongodb://mongodb´


#### To run using the IDE:

1. Edit the **spring.data.mongodb.uri** property in **application.properties**:
	- If you have already performed `docker-compose up` you can use the dockerized MongoDB instance. Just remember to stop the flightinfoapi container to prevent port conflicts: ´docker stop flightinfoapi´.  
	- For convenience, a Mongo Atlas instance has also been provided (check application.properties for more info).
	
2. Import the FlightInfoApi project into your IDE. If using Eclipse or SpringToolSuite it may be necessary to update the project:
	**Right click FlightInfoApi project -> Maven -> Update Project -> Select: Force update of Snapshots/Releases -> OK**

3. Run as SpringBoot Application


## How to use

After deploying the API open a browser and go to http://localhost:8080/swagger-ui
 
There you will see the api's Swagger page which contains 6 endpoints:
 
 * **FLIGHTS - Flight Information**
 	
	- GET Flights (http://localhost:8080/flights)
		- Retrieves a list of Flights based on the specified parameters.
		- Ex: GET localhost:8080/flights?from=LIS&to=OPO&dateFrom=30/12/2019&dateTo=31/12/2019
	
	- GET Average Flight Prices (http://localhost:8080/flights)
		- Returns the average prices for the list of flights that match the specified parameters.
		- Ex: GET localhost:8080/flights/avg?from=LIS&to=OPO&dateFrom=30/12/2019&dateTo=31/12/2019

 * **REQUESTS - Get info or delete requests made to the 'Flights' endpoints**
 	
	- GET All Requests (http://localhost:8080/requests)
		- Retrieves a list of requests made to 'Flights' endpoints
	
	- GET Request By Id (http://localhost:8080/requests/{id})
		- Retrieves the request with the given id
		- Ex: GET http://localhost:8080/requests/5dee6a2bd1db84664aa33a19
		
	- DELETE All Requests (http://localhost:8080/requests)	
		- Deletes all request info from database
		- DELETE http://localhost:8080/requests

	- DELETE Request By Id (http://localhost:8080/requests/{id})
		- Deletes the request with the given id
		- DELETE http://localhost:8080/requests/5dee6a2bd1db84664aa33a19
		

## Code Breakdown

The application is built using Spring Boot and written in java. It is based on Spring MVC and also uses Spring Web Client to make requests to the external API provided by Kiwi: https://docs.kiwi.com/

As seen above, the application is split into two main components: Flights and Requests.

### Flights

Used to retrieve information about flights as well as average flight and bag prices.  

**Basic Flow:**

1. When a request is made to /flights or /flights/avg, it is handled by Controller methods in **FlightInfoController**.
2. The controller methods perform validation on the specified query parameters (**FlightCriteria**) by calling **FlightCriteriaValidator**.
3. If the criteria are valid, the controller calls the corresponding **FlightInfoService** method. This class is used to remove business logic from the controllers and keeping them as simple and clean as possible.
4. **FlightInfoService** takes the **FlightCriteria** specified and calls the **getFlights** method on **KiwiWebClient**.
5. **KiwiWebClient** makes the request to Kiwi's Flights API by using the same query parameters that it received in the **FlightCriteria** argument. This will obtain a list of flights that is then mapped into a **KiwiFlightsResponse** object and returned to **FlightInfoService**.
6. **FlightInfoService** performs business logic over the retrieved **KiwiFlightsResponse**. It will either map it into a list of **FlightsResponse** or calculate the average flight and bag prices for all the flights returned and build a **FlightsAverageResponse**.
7. The controller receives the data processed by **FlightInfoService** and wraps it in a **ResponseEntity** with the appropriate Http Status code.

One of the requirements of this challenge was to store the data of requests made to Flights endpoints in a database.
In this case, the database used is MongoDB.

**How is request data stored:**

Requests made to a controller can be intercepted using **org.springframework.web.servlet.handler.HandlerInterceptorAdapter**.

- When a request is made to "/flights" or "/flights/avg", it is intercepted by **FlightInfoApiInterceptor**.
- By overriding **HandlerInterceptorAdapter's afterCompletion** method, it is possible to get information about the request and the response.
- This information is used to create and instance of **FlightApiRequestEntity** which is stored in MongoDb by calling the **FlightsRequestRepository** interface which extends **org.springframework.data.mongodb.repository.MongoRepository**.

