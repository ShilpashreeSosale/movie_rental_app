# Movie Rental Management System

## Description
This is a Spring Boot application that demonstrates a simple REST API for fetching movie rental information for the given customer.
This project uses **Liquibase** for managing database migrations. Liquibase allows us to version and track changes in the database schema in a safe and easy-to-manage way.

## Prerequisites
- Java 17+
- Maven
- Spring Boot 3.x
- Postgres
- Ensure that your database is up and running.

### Tools used 
- Postman
- Docker desktop
- pgAdmin
- IntelliJ IDEA 

### Technologies used
- Spring Boot
- Spring Data JPA
- PostgreSQL (Database)

### Ensure that your database is up and running.
- Install Doker desktop to pull the PostgreSQL image from Docker hub 
- in console type the below commands
- docker pull postgres
- docker run --name postgresql -e POSTGRES_USER=myusername -e POSTGRES_PASSWORD=mypassword -p 5432:5432 -v /data:/var/lib/postgresql/data -d postgres

## How to access the DB and data
- open pgAdmin and register the server with the details below
- Please provide name in General tab(movieLocalDB)
- in connections tab please provide details as below.
- Host as localhost
- Port as (deafault) 5432
- Username as myusername
- Password as mypassword
- database : postgres
- test connection and save

## How to Compile and Run

### Using Maven
- run the below commands in the terminal
- mvn clean install
- mvn spring-boot:run

### API end points and request body (Tested using postman)
- Metod : Post
- URL : http://localhost:9082/customer/movie/rentalInfo
- Request body:(JSON)

- valid data:
{
    "customerId":"CUStomer", 
    "rentals":[
               {
                "movieId":"F001",
                "rentalPeriod":3
                },{
                "movieId":"F002",
                "rentalPeriod":1
                }
    ]
}

- Invalid data with empty customer Id:
{
    "customerId":"", 
    "rentals":[
               {
                "movieId":"F001",
                "rentalPeriod":3
                },{
                "movieId":"F002",
                "rentalPeriod":1
                }
    ]
}











"# movie_rental_app" 
"# movie_rental_management_system" 
"# movie_rental_app" 
