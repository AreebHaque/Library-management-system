# Library Management System

A full-stack Library Management System built with Spring Boot to automate library operations.This system provides an efficient digital solution for book reservations, user management, and library inventory tracking. 

## Features

###  Authentication & Authorization
- **Role-based access control** (Student & Librarian)
- **Secure session management**
- **Automatic login redirection**

### Book Management
- **Browse all available books**
- **Advanced search functionality** (title, author, ISBN)
- **Real-time availability status**
- **Book reservation system**

### Smart Reservation System
- **1-hour automatic reservation expiry**
- **Scheduled cleanup of expired reservations**
- **Reservation cancellation capability**
- **Real-time status updates**

### Admin Dashboard
- **Complete book CRUD operations**
- **Student account management**
- **System monitoring**
- **Manual reservation management**

### User Experience
- **Modern dark theme UI**
- **Fully responsive design**
- **Mobile-friendly interface**
- **Intuitive navigation**

## Technology Used

### Backend
- **Spring Boot 3.5.8** - Application framework
- **Spring MVC** - Web framework
- **Spring Data JPA** - Database abstraction
- **Spring Security** - Authentication
- **Java 21** - Programming language

### Frontend
- **Thymeleaf** - Server-side templating
- **HTML5, CSS3** - Frontend technologies

### Database & Tools
- **H2 Database** - Embedded database
- **Maven** - Build tool
- **Git** - Version control






## Steps to Install and run the project

### Step 1: Clone the Repository

```bash
# Clone the project from GitHub
git clone https://github.com/yourusername/library-management.git

# Navigate to project directory
cd library-management
```
### Step 2: Verify Java and Maven Installation

```bash
# Check Java version (should be 17 or higher)
java -version

# Check Maven version (should be 3.5 or higher)
mvn -version
```
### Step 3: Build the Project
```bash
# Clean and compile the project
mvn clean compile

# Or build the complete package (includes tests)
mvn clean package
```
### Step 4: Run the Application
```bash
# Method 1: Using Maven Spring Boot plugin
mvn spring-boot:run

# Method 2: Using the generated JAR file
mvn clean package
java -jar target/library-management-0.0.1-SNAPSHOT.jar
```
### Step 5: Access the Application

Main Application: http://localhost:8080/books

H2 Database Console: http://localhost:8080/h2-console

    JDBC URL: jdbc:h2:file:./data/librarydb

    Username: sa

    Password: (leave empty)

Access the Deployed website

Website: https://library-management-system-43i2.onrender.com/books

**Note**

H2 Database Console is disabled in production for security reasons

Database access is only available during local development

The application may take 30-60 seconds to load on first access due to Render's free tier




## Instructions for testing

### Test Accounts

The system comes with pre-configured test accounts:

| Role      | Username    | Password | Purpose                         |
|-----------|-------------|----------|---------------------------------|
| Student   | student1    | pass123  | Testing student features        |
| Student   | student2    | pass123  | Additional test account         |
| Librarian | librarian1  | admin123 | Testing admin features          |

### Test Scenarios

#### Authentication

    Login with test accounts → Verify redirect to books page

    Invalid login → Check error message

    Logout → Verify session cleared

#### Book Operations

As Student:

    Browse all books in grid layout

    Search by title/author/ISBN

    Reserve available books → Verify status changes

    Cancel reservations → Verify book becomes available

As Librarian:

    Add new books via admin panel

    Delete books from system

    Unreserve books manually

Reservation System

    View reservations in "My Reservations" page

    Test 1-hour expiry (auto-cleanup runs every 5 minutes)

    Cancel active reservations

Admin Dashboard

    Access control - Students cannot access admin panel

    User management - Add/delete student accounts

    System monitoring - View all books and users

Database Testing

    Access H2 Console at /h2-console

    Verify data integrity with SQL queries

    Check reservation relationships

## Screenshots

