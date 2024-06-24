# Event Microservice Project

## Introduction

After analyzing the requirements, the following solution has been developed, which can be visualized in the attached diagram:

![Solution Diagram](path/to/image.png)

## Logical View of the Solution

A single microservice named `events` has been chosen, which manages two main processes:

1. **Extraction and Feeding of the PostgreSQL Database**:
   - The data will be stored in a PostgreSQL database to ensure its persistence, guaranteeing availability even if the external provider is down.
   - A dedicated service will make calls to the external provider daily at night, when application activity is minimal, to avoid affecting its performance.
   - For this task, WebClient from Spring WebFlux will be used, a non-blocking alternative ideal for handling a high volume of simultaneous requests.

2. **Exposure of the `/search` Endpoint**:
   - This endpoint will obtain the necessary data from the PostgreSQL database, filtering by the start and end date of the events.
   - Swagger API will be used to define the endpoint specifications in a `swagger.yaml` file, facilitating integration with third parties.

## Sequence Diagram

The following sequence diagrams illustrate both processes:

![Sequence Diagram](path/to/diagram.png)

## Hexagonal Architecture

A hexagonal architecture has been adopted for the microservices, dividing the application into layers independent of each other. This structure separates business logic from the user interface and external infrastructure, facilitating maintenance and testing.

![Hexagonal Architecture](path/to/architecture.png)

### Benefits of Hexagonal Architecture

- **Layer Independence**: Internal layers do not depend on external ones. The domain layer does not depend on any other layer, and the use case layer connects through ports to the domain layer.
- **SOLID Principles**: This architecture facilitates the adherence to SOLID principles, improving code quality, maintainability, and scalability.
- **Unit Testing**: The clear structure allows for effective unit testing using JUnit and Mockito.

## Architecture Components

- **Application**: Implements business logic and handles HTTP requests through the REST controller.
- **Domain**: Defines the internal logic of the application and the data models.
- **Infrastructure**: Manages the connection with the database (PostgreSQL) and external services (WebClient).
- **EventsApplication.java**: Main entry point of the application.

## Applied SOLID Principles

1. **Single Responsibility Principle (SRP)**: Each class has a single responsibility.
2. **Open/Closed Principle (OCP)**: The system is extensible without needing to modify its basic structure.
3. **Liskov Substitution Principle (LSP)**: Derived classes can substitute their base classes without issues.
4. **Interface Segregation Principle (ISP)**: Specific interfaces are created for each use case.
5. **Dependency Inversion Principle (DIP)**: Abstractions are used to decouple implementation details.

## Persistence in PostgreSQL Database

Data from the external provider is stored in PostgreSQL, with all tables indexed to ensure optimal query performance.

![Database Diagram](path/to/bbdd.png)

## Testing and Coverage with Jacoco

Unit tests have been performed for all classes, achieving nearly 100% coverage. These tests were performed using JUnit and Mockito, and a coverage report was generated with Jacoco, available at `target/site/jacoco/index.html` after executing `mvn clean package`.

![Coverage Report](path/to/jacoco.png)

## Observability: Traceability and Monitoring

A logging and traceability strategy has been implemented with the slf4j library to facilitate maintenance and problem resolution.

## Performance

- **Use of WebClient**: Handles a high number of simultaneous requests without blocking the system.
- **Nightly Scheduling**: Data extraction tasks are executed at night to avoid system overload.
- **PostgreSQL Optimization**: Use of indexes to improve query performance.

## Building and Running the Application

The project includes a `make` file with the following options:

- `make build`: Compiles and packages the project.
- `make run`: Starts the application using `docker-compose up`, configuring a PostgreSQL database and executing the `init.sql` script.
- `make clean`: Shuts down the Docker container and cleans the project with `mvn clean`.

To test the application:

1. `make build`
2. `make run`

Access Swagger to test the exposed endpoint at: `http://localhost:8080/swagger-ui/index.html`

![Swagger UI](path/to/swagger.png)

## Next Steps

- **Monitoring and Traceability**: Integrate Spring Boot Actuator with Prometheus and Grafana.
- **Additional Testing**: Perform integration, contract, and load testing.
- **Performance Optimization**: Implement caching systems and load balancers.
- **Continuous Integration**: Establish a CI/CD pipeline with tools like Travis or GitHub Actions.
- **Horizontal Scalability**: Use Kubernetes and queue mechanisms like Kafka or RabbitMQ.

### Additional Considerations

As the system grows, consider further decomposing the microservices to handle different aspects of events efficiently.

---

**Note**: When the application is started, a scheduled task is configured to run every 5 minutes for testing. In production, this task should run once a day at 3 am.

---

**Docker Commands**:

- View containers: `docker ps -a -q`
- Stop executions: `docker-compose down -v`
- Test friendly endpoint: `http://localhost:8080/swagger-ui/index.html`

---

**Endpoint Tests**:

- **200**: Successful response.
- **400**: Bad request error.
- **500**: Server error.

---

**Swagger Validation**:

The `swagger.yaml` file has been validated using [Swagger Editor](https://editor-next.swagger.io/).

![Swagger Validation](path/to/swagger_validation.png)
