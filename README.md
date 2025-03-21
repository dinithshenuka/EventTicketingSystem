# Real-Time Event Ticketing System (VENTIX)

A real-time event ticketing system built with Angular and Spring Boot, implementing Object-Oriented Programming principles and the Producer-Consumer pattern to simulate a dynamic ticketing environment.

## Technology Stack

### Frontend
- **Framework**: Angular 18.2.12
- **UI Library**: Bootstrap 5.3.3
- **Icons**: Font Awesome 6.7.1
- **HTTP Client**: Angular HttpClient
- **State Management**: RxJS 7.8.0
- **Real-time Updates**: Observable pattern
- **Package Manager**: npm
- **Deployment**: Vercel

### Backend
- **Framework**: Spring Boot 3.3.5
- **Java Version**: JDK 21
- **Database**: MySQL
- **ORM**: Hibernate/JPA
- **Concurrency**: Multi-threading with Async operations
- **Design Pattern**: Producer-Consumer pattern
- **Serialization**: GSON 2.10.1
- **Build Tool**: Maven

## Project Structure

### Frontend (Angular)
- **src/**
  - **app/**: Angular components, services, and modules
  - **assets/**: Static assets (images, styles, etc.)
  - **environments/**: Environment-specific configurations

### Backend (Spring Boot)
- **src/main/java/**: Java source code
  - **com/ventix/**: Main package
    - **controller/**: REST controllers
    - **service/**: Business logic
    - **repository/**: Data access layer
    - **model/**: Entity classes
- **src/main/resources/**: Configuration files

## Features

- **Real-time Ticket Management**
  - Live ticket availability updates
  - Concurrent ticket releases and purchases
  - Automated ticket pool management
  - Dynamic ticket status tracking

- **Vendor Portal**
  - Vendor registration and authentication
  - Event ticket creation and management
  - Real-time ticket inventory tracking
  - Multiple event support per vendor

- **Customer Experience**
  - Easy ticket purchase flow
  - Real-time availability checks
  - Event browsing and searching

- **Admin Dashboard**
  - System configuration management
  - Real-time monitoring of tickets
  - Simulation controls for testing

## Setup & Installation

### Prerequisites
- Node.js and npm
- Angular CLI 18.2.12
- Java Development Kit (JDK)
- MySQL

### Installation
1. **Clone the repository**
   ```sh
   git clone https://github.com/dinithshenuka/EventTicketingSystem.git
   cd EventTicketingAngular
   ```

2. **Install frontend dependencies**
   ```sh
   npm install
   ```

3. **Install backend dependencies**
   ```sh
   cd backend
   mvn install
   ```

### Development Server
1. **Frontend**
   ```sh
   ng serve
   ```
   - Navigate to http://localhost:4200/

2. **Backend**
   ```sh
   mvn spring-boot:run
   ```
   - API will be available at http://localhost:8080/

### Build
1. **Frontend**
   ```sh
   ng build
   ```
   - Build artifacts will be stored in the dist/ directory

2. **Backend**
   ```sh
   mvn package
   ```
   - The JAR file will be created in the target/ directory

## API Documentation

### Endpoints
- **GET /api/tickets**: Retrieve all tickets
- **POST /api/tickets**: Create a new ticket
- **PUT /api/tickets/{id}**: Update a ticket
- **DELETE /api/tickets/{id}**: Delete a ticket

### Models
- **Ticket**
  - `id`: Unique identifier
  - `event`: Event details
  - `price`: Ticket price
  - `status`: Ticket status (available, sold, etc.)

### Error Handling
- **400 Bad Request**: Invalid input
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error


