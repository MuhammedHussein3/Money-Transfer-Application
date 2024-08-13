# Money Transfer Application

## Overview

The **Money Transfer Application** is a microservices-based banking system designed to handle internal and external money transfers, account management, and more. This guide will help you set up and run the application.

## Architecture

![System Architecture](https://github.com/MuhammedHussein3/Money-Transfer-Application/blob/master/Money-Transfer-microservice.png)

### Components
- **Account Service**: Manages user accounts, including creation and balance management.
- **Transaction Service**: Handles transactions and transaction history.
- **Notification Service**: Sends notifications using Kafka and RabbitMQ. Utilizes design patterns for scalability and maintainability.
- **User Service**: Manages user profiles and authentication.
- **Bank Gateway Service**: Routes requests using Spring Cloud Gateway with Kotlin and Java.
- **Message Brokers**:
  - **Kafka**: For reliable and scalable messaging.
  - **RabbitMQ**: For inter-service communication and event handling.
- **Databases**:
  - **PostgreSQL**: Stores structured data.
  - **MongoDB**: Handles non-relational data.
- **Caching**: Redis for improved performance.
- **Monitoring**: Zipkin for tracing and performance monitoring.
- **Circuit Breaker**: Resilience4j to manage service failures and ensure resilience.

## Features

### User Accounts and Profiles
- **Profile Creation**: Add user profiles with essential details.
- **Account Linking**: Connect profiles to bank accounts.
- **Account Management**: View and update account details.

### Transfers
- **Internal Transfers**: Transfer funds between your accounts.
- **External Transfers**: Send money to other users or external accounts.

### Account Management
- **Balance Inquiry**: Check account balance.
- **Transaction History**: View past transactions.
- **Favorite Accounts**: Save and manage favorite accounts for quicker transfers.

### Currency Conversion
- **Current Currency**: Display the user's country currency.
- **Currency Conversion**: Convert between different currencies.

### Sub-Accounts
- **Creation**: Create sub-accounts for budgeting.
- **Management**: Organize debits and credits for better financial tracking.

### Security
- **JWT Authentication**: Secure access using JWT tokens.
- **Token Expiry**: Tokens expire after 30 minutes.

### Email Notifications
- **Alerts**: Receive notifications for transactions and updates.
- **Templates**: Professional emails using Thymeleaf.

### Messaging
- **Kafka**: For asynchronous messaging between services.
- **RabbitMQ**: For service-to-service communication.

### Circuit Breaking
- **Resilience4j**: Implement circuit breakers to handle service failures.

### Routing
- **Spring Cloud Gateway**: Manages routing and cross-cutting concerns.

### Data Storage
- **PostgreSQL**: Relational database.
- **MongoDB**: NoSQL database.

## Technologies

- **Backend**: Java, Kotlin, Spring Boot 3
- **Security**: Spring Security 6, JWT Tokens
- **Data Storage**: PostgreSQL, MongoDB
- **Caching**: Redis
- **Messaging**: Kafka, RabbitMQ
- **Monitoring**: Zipkin
- **API Documentation**: Swagger
- **Email Notifications**: JavaMailSender, Thymeleaf
- **Design Patterns**: Applied in the Notification Service
- **Circuit Breaking**: Resilience4j
- **Routing**: Spring Cloud Gateway

## Getting Started

### Prerequisites
- Java 17+
- Kotlin
- Docker
- Docker Compose

### Setup

1. **Clone the Repository**
    ```bash
    git clone https://github.com/MuhammedHussein3/Money-Transfer-Application.git
    ```

2. **Configure Environment**

    - Create a `.env` file and add the required environment variables for database connections, Redis, Kafka, RabbitMQ, and other services.

3. **Run Locally**

    ```bash
    docker-compose up
    ```


## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your improvements or fixes.



## Contact

For questions or issues, please contact [My Email](muhammadhussein2312@gmail.com).
