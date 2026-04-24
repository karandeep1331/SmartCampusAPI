# 5COSC022W Client-Server Architectures Coursework 

# 0verview
I have Developing a API for a Smart Campus system using Java and JAX-RS, the application manages resources such as rooms and sensors through HTTP methods like GET and POST. It requires setting up a Maven project, creating model and resource classes, and ensuring proper handling of requests with suitable responses and validation. And testing the system of the GET and POST with postman application.

# Run & Instructions
1. Clone the project ```https://github.com/karandeep1331/SmartCampusAPI.git```
2. Configure the ```pom.xml``` and tomcat server
3. Clean & build the project
4. run the project
5. The discvory endpoint ```http://localhost:8080/SmartcampusAPI-CSA/api/v1```


# Discovery endpoint
```bash
curl -X GET http://localhost:8080/SmartcampusAPI-CSA/api/v1
```
# Create a room
```bash
curl -X POST http://localhost:8080/smartCampusAPI-CSA/api/v1/rooms
-H "Content-Type: application/json" \
-d '{ "id": "LIB-301","name": "Library Quiet Study", "capacity": 100 }'
```
# Get all rooms
```bash
curl -X GET http://localhost:8080/smartCampusAPI-CSA/api/v1/rooms
```
# Get room by ID
```bash
curl -X GET http://localhost:8080/SmartCampusAPI-CSA/api/v1/rooms/LIB-301
```

# Delete a room
```bash
curl -X DELETE http://localhost:8080/SmartCampusAPI-CSA/api/v1/rooms/LIB-301
```
# Create a sensor
```bash
curl -X POST http://localhost:8080/smartCampusAPI-CSA/api/v1/sensors
-H "Content-Type: application/json" \
-d '{"id": "LABSEN-01", "type": "Temperature","status": "ACTIVE","currentValue": 22.5,
  "roomId": "LAB-01"}'
```
# Get all sensors
```bash
curl -X GET http://localhost:8080/smartCampusAPI-CSA/api/v1/sensors
```
# Filter Sensor
```bash
curl -X  GET http://localhost:8080/SmartCampusAPI-CSA/api/v1/sensors?type=Temperature
```
# Add Sensor Reading

```bash
curl -X  POST http://localhost:8080/SmartCampusAPI-CSA/api/v1/sensors/LABSEN-01/readings
-H "Content-Type: application/json" \
-d '{"id":reading-001, "reading-002","timestamp": 1710001200000,"value": 25.4}'
```
# Get Sensor Reading
```bash
curl -X GET http://localhost:8080/SmartCampusAPI-CSA/api/v1/sensors/LABSEN-01/readings
```

# Report
# Part 1: Service Architecture & setup

Q1.1 Project & Application Configuration

JAX-RS resource classes follow a per-request model, where a new instance is generated for each request in the application lifecycle. This reduces problems associated with running multiple tasks, because each request is processed independently without any interaction with other requests. which is shared data will be used in most cases, such as data maps or arrays ```rooms``` or ```sensor```. As a result, clients may attempt to access or modify the shared information, creating a race condition where the data could be updated or changed incorrectly. To prevent race conditions, programmers should ensure that shared data is protected, for instance, through thread-safe data structures or appropriate synchronisation.

Q1.2 The ”Discovery” Endpoint

Hypermedia (HATEOAS) is an advanced RESTful design principle that enables clients to discover and navigate an API through links included in responses, rather than relying on hardcoded endpoints. In the Smart Campus API, responses from endpoints such as ```/api/v1``` the discovery endpoint or ```/api/v1/rooms``` can include links to related resources like ```/api/v1/sensors``` or ```/api/v1/sensors/temp-001/readings```. This approach benefits client developers by making the API self-descriptive, allowing clients to follow links instead of relying on static documentation, which may become outdated. It also reduces tight coupling between the client and server, meaning the API can evolve without breaking existing clients, improving flexibility.

# Part 2: Room Management

Q2.1 Room Resource Implementation
Returning only the IDs of rooms minimises network bandwidth usage because the response size is smaller, making the API more efficient when handling large amounts of data. This increases client-side processing, as the client must make additional requests (e.g., GET ```/rooms/temp-001``` to retrieve full details. Returning full room objects provides all necessary information in a single response, reducing the need for extra API calls and simplifying client-side logic.

Q2.2 Room Deletion & Safety Logic

The DELETE command calls result in the same final state of the system.  When the client makes a DELETE call to delete a particular room, DELETE ```/rooms/temp-001```, the system first checks if there are any sensors attached to it. In case there are sensors attached, the system does not allow the room to be deleted and returns a 409 Conflict error code. However, when there are no sensors attached, the room is successfully deleted from the system. if the user sends a DELETE request again for a room that has already been deleted, the room no longer exists in the system, then a 404 Not Found error message response is returned.

# Part 3: Sensor Operations & Linking

Q3.1 Sensor Resource & Integrity

The @Consumes(MediaType.APPLICATION_JSON), the POST endpoint only accepts request bodies in JSON format. If a client sends data in a different format, such as text/plain or application/xml, the request is rejected with an HTTP 415 Unsupported Media Type response. This occurs before the resource method is executed, as JAX-RS cannot match the request to a method that supports the given media type. JAX-RS relies on message body readers to convert incoming data into Java objects, and if no suitable reader is available for the provided content type, the request cannot be processed and fails.

Q3.2 Filtered Retrieval & Search

Using @QueryParam for filtering ```/api/v1/sensors?type=LIGHT```is  considered higher because it aligns with RESTful principles for querying collections. Query parameters are designed for filtering, sorting, and searching within a resource collection without altering the identity of the resources themselves. In this case, ```/api/v1/sensors``` still represents the same collection, and the query parameter simply refines the result set. The filter in the path ```/api/v1/sensors/type/LIGHT``` handles the filter as a sub-resource, which can lead to less flexible and less scalable designs. Query parameters allow multiple filters to be combined easily ```?type=LIGHT&status=ACTIVE```, where path-based filtering would require more complex and rigid URL structures.
 
# Part 4: Deep Nesting with Sub – Resources

Q4.1 The Sub-Resource Locator Pattern

The Sub-Resource Locator pattern helps keep an API organised by splitting logic into smaller, focused classes instead of putting everything into one large controller. For example, a SensorResource can pass handling of ```/sensors/temp-001/readings``` to a separate SensorReadingResource.This makes the code easier to read, maintain, and extend. Each class has a clear responsibility, which reduces complexity as the API grows. Compared to having all nested routes in one class, this approach is more scalable and easier to manage in large systems.

# Part 5: Advanced Error Handling & Exception Mapping

Q5.2 Dependency Validation (422 Unprocessable Entity)

HTTP 422 is where you cannot process an Entity and is used when the request is valid but the data inside it is incorrect. The server understands the request, the roomId does not exist so it cannot process it. The client correctly sends a POST request to ```/sensors``` with a well-formed JSON body, so the server can understand and process the request format. The ```roomId``` provided in the request refers to a resource that does not exist. This means the issue is not with the endpoint or URL which would result in a 404 , but with the data inside the request. HTTP 422 is appropriate because it indicates that the server understands the request but cannot process it due to errors in the input, making it 404 Not Found response.

Q5.4 The Global Safety Net (500)

External access to internal stack traces from Java presents several security threats since it gives away too much sensitive information related to how the program works internally/ There is also the danger that stack traces give away other details such as frameworks, library packages, and server configurations, allowing for further targeted attacks using existing exploits. Sometimes error messages contain fragments of data that should not be there at all because of poor handling of the information. Using generic HTTP 500 Internal Server Error messages removes those threats.
