# Project Title

As per the provided requirements, create a RESTful API to manage phone book for customers.
Where there is 1 to many relationship between a customer and their phone numbers

---

## Tech Stack

- Language: Java 17
- Framework: Spring Boot 3.4.5
- Build Tool: Maven 3.9.9
- Database: H2 In-Memory DB
- IDE: I have used IntelliJ Idea

---

## Installation

```bash
# Clone the repo
git clone git@github.com:aditya-agashe/phone.book.git

# Navigate into the project
cd your-repo

# Install dependencies 
mvn clean install

# Start the application
mvn spring-boot:run OR ./mvnw spring-boot:run

```

---

## Database

In-Memory H2 database is hosted at `http://localhost:8080/h2-console`
Use username as sa. Click connect. I haven't set password

Because the data.sql file exists in src/main/resources there is no need to run any scripts.
It is intuitive to run the SQL scripts like any other DB client.

There are 2 tables: customer and phone_number. 1 Customer - Many Phone Numbers
Please check the tables / schemas.

---

## API Specification - OpenAPI / Swagger
I have gone with code first approach. I had idea on how would I implement.
Hence, I have used open api / swagger dependency to generate the documentation.
Start the Spring Boot application and visit site `http://localhost:8080/swagger-ui/index.html`
to view the API specifications

---

## Assumptions
1. There was no requirement given on what customer details will be stored, hence I have saved only Customer Name.
Ideally it should be First Name, Last Name etc. But some people have 3 parts to the names such as Robert Downey Jr.
Hence went with one column.
2. I haven't implemented pagination as I spent a lot of time in writing unit tests to brig up the coverage. 
Generally for GET LIST responses pagination is recommended.
3. For code coverage. I have used IntelliJ Idea inbuilt coverage tool.
4. I was in 50% mind to either use PUT or PATCH to activate the phone number. 
Going with PUT approach I would have created more work around validations for phone numbers
Hence I have implemented PATCH endpoint, where ONLY isActivated field can be updated.
`isActivated` is optional. i.d If passed it will set else the resource will be unchanged.
5. I have tried to keep in mind REST API design principles given by Roy Fielding

---

## SonarQube Coverage
1. Create the Sonar docker container `docker run -d --name sonar -p 9000:9000 sonarqube`
2. Standup Sonar on port `9000` locally. 
3. Go to url `http://localhost:9000/`. Sign-In
4. Create a project with name and key `phone.book`
5. Create secret sonar token. Save the token in environment variable named SONAR_TOKEN
`set SONAR_TOKEN=your-token-here`
`echo $env:SONAR_TOKEN`
6. Run command `mvn clean test sonar:sonar`
7. View the analysis report on page `http://localhost:9000/dashboard?id=phone.book` 