# account-application

This application provides...
-----

## Requirements:

- Java 17
- Gradle 8.4
- Docker
- Currently, only tested on Fedora 39 and IntelliJ IDEA

## How to run:

- `cd account-application`
- `gradle clean build`
- `gradle bootRun`

## Run with Docker:

### Using the existing JAR file:

- `cd account-application`
- `docker build --no-cache --build-arg JAR_FILE=account-application.jar -t renvl/account-application .`
- `docker run -p 8080:8080 -t renvl/account-application`

### Compiling the application:

- `cd account-application`
- `gradle clean build`
- `docker build --no-cache --build-arg JAR_FILE=build/libs/\*.jar -t renvl/account-application .`
- `docker run -p 8080:8080 -t renvl/account-application`

## API Pages (URLs):

- http://localhost:8080/swagger-ui/index.html to see a Swagger-UI formatted documentation.

Note that, in the API documentation pages, documentations for POST requests for the account application can be seen.
Moreover, in Swagger UI for example, making a query is quite easy with example formats.

## Parametrization:

To connect the embedded H2 Database use the parameters according `src/main/resources/application.properties`

## License

account-application is distributed under the terms of the
[MIT License](https://choosealicense.com/licenses/mit).