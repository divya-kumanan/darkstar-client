DarkStar Enterprises Client Application
# Building and Running darkstar-client in Docker
## Prerequisites
* Docker
* JDK 11
* Maven
* Make sure darkstar-server application and it has the mission and shuttle data
## Building the application
1. Clone the repository to your local machine:
```
https://github.com/divya-kumanan/darkstar-client.git
```
2. Navigate to the root directory of the project:
```
cd darkstar-client
```
3. Build the application using Maven:
```
mvn clean install
```
This will build the application and create a JAR file in the `target` directory.

## Building the Docker image
1. Build the Docker image:
```
docker build -t darkstar-client .
```
This will build a Docker image named `darkstar-client` using the Dockerfile in the root directory of the project.

## Running the Docker container
1. Run the Docker container:
```
docker run -p 8080:8080 darkstar-client
```
This will start the container and map port 8080 in the container to port 8080 on your host machine.

2. Access the darkstar-client in your web browser:
```
http://localhost:8080/actuator/health
```
This will bring up the health status of your Spring Boot application.

## Stopping the Docker container
1. To stop the Docker container, open a new terminal window and run the following command to list all running containers:
```
docker ps
```
2. Find the container ID for `darkstar-client` in the output.
3. Stop the container by running the following command:
```
docker stop container_id
```
Replace `container_id` with the actual container ID for `darkstar-client`.

## View the Postman Collection
1. Open the Postman App
2. Import the collection `DarkStar Client.postman_collection.json` and `DarkStarClient Local.postman_collection.json`  from the root folder of darkstar-client
3. Import the collection `DarkStar Server.postman_collection.json` and `DarkStarServer Local.postman_collection.json`  from the root folder of darkstar-client.
4. Run the server/client request based on the server/client environment

## View Open-Api Specification(Swagger) for darkstar-client
1. Access the swagger document [openapi yaml file]
```
http://localhost:8080/v3/api-docs
```
2. Access the swagger document in UI
```
http://localhost:8080/swagger-ui/index.html
```