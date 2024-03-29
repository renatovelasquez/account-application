# account-application

This application provides a small core banking solution to keep track of current accounts, balances, and transaction
history and the capability to publish messages into RabbitMQ for downstream consumers.
-----

## Requirements:

- Java 17
- SpringBoot 3
- MyBatis 3
- Gradle 8.4
- Postgres 16.2
- RabbitMQ 3.13.0
- Docker 25
- JUnit
- Jacoco (Test coverage 80%)
- Currently, only tested on Fedora 39 and IntelliJ IDEA

## Running using Docker Compose:

```bash
cd account-application
docker-compose -f docker-compose.yml up
```

## How to run locally:

```bash
cd account-application
gradle clean
gradle bootRun
```

## Run with Docker:

```bash
cd account-application
gradle clean build
docker build --no-cache --build-arg JAR_FILE=build/libs/\*.jar -t renvl/account-application .
docker run -p 8080:8080 -t renvl/account-application
```

## Database Design

![account_application_db.png](src/main/resources/account_application_db.png)

Available Customers ID

| customer\_id |
|:-------------|
| CUSTOMER001  |
| CUSTOMER002  |
| CUSTOMER003  |

## API:

### http[]()://localhost:8080/api Endpoint

#### /accounts/v1/

* `POST` : Create a new account

    ```bash
    curl --request POST \
      --url http://localhost:8080/api/accounts/v1 \
      --header 'Content-Type: application/json' \
      --data '{
      "customerId": "CUSTOMER001",
      "country": "Estonia",
      "currencies": [
      "EUR", "USD"
      ]
      }'
    ```

#### /accounts/v1/:id

* `GET` : Get an Account by ID

#### /transactions/v1/

* `POST` : Create a new transactions for an Account

    ```bash
    curl --request POST \
    --url http://localhost:8080/api/transactions/v1 \
    --header 'Content-Type: application/json' \
    --data '{
      "accountId": 1,
      "amount": 123.45,
      "currency": "EUR",
      "direction": "IN",
      "description": "EUR deposit."
    }'
    ```

#### /transactions/v1/:id

* `GET` : Get all transactions by Account ID

## Swagger-ui

http://localhost:8080/swagger-ui/index.html Swagger-UI formatted documentation.

NB!, in the API documentation pages, documentations for POST and GET requests for the account-application can be seen.
Moreover, in Swagger UI for example, making a query is quite easy with example formats.

## RabbitMQ Integration

http://localhost:15672 RabbitMQ Management UI

## Parametrization:

To connect Postgres and RabbitMQ use the properties according
to [application.properties](src/main/resources/application.properties)

## License

account-application is distributed under the terms of the
[MIT License](https://choosealicense.com/licenses/mit).
