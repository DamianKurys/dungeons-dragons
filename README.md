# Dungeons & Dragons (still in progress)

##Tech stack
- Java 21
- Spring Boot
- Hibernate
- MySql
- Maven
- Lombok
- JUnit 5, test cointainers
- React

## Project structure
dungeons-dragons/
├─ backend/Bestiariusz                  
│  ├─ src/main/java/...         # controllers, services, repository, models
│  ├─ src/main/resources/       # application.properties + profile dev/prod
├─ frontend/fantasy-frontend    # React app
│  ├─ src/
├─ docker-compose.yml           # MySQL 
└─ README.md


## Quick start
   Docker: MySql (port 3307)
   
   docker compose up -d mysql
   
   spring.datasource.url=jdbc:mysql://localhost:3307/Bestiariusz?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=root

   backend 
   cd backend/Bestiariusz
   mvn spring-boot:run -Dspring-boot-run.profiles=dev
   
   API: http://localhost:8080
   Swagger UI: http://localhost:8080/swagger-ui.html

   frontend
   cd frontend/fantasy-frontend
   npm install
   npm start
   Front: http://localhost:3000
  
