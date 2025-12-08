# Bigtablet Homepage Server
### This project was developed using the following technologies.
- **Spring Boot** (3.5.7)
- **Java** (21)
- **MySQL** (8.0)
---
### Project execution steps:
1. JDK 21 or higher, Gradle, and Git must be installed.
2. Enter the following command in the terminal to clone this project to your local machine.
   ```bash
   git clone https://github.com/Bigtablet/bigtablet-homepage-server.git
   ```
3. After moving to the folder where the project is stored, run the following commands in order. (You must enter the required keys defined in the `yml` file yourself.)
   - If you're on Linux/MacOS
     ```bash
      ./gradlew build
      ./gradlew bootRun
      java -jar build/libs/bigtablet-homepage-server-1.3.0.jar
     ```
   - If you're on Window
     ```bash
      gradlew build
      gradlew bootRun
      java -jar build/libs/bigtablet-homepage-server-1.3.0.jar
     ```
4. If a port conflict occurs on port 8080, change the port number at the end of the command below to one that is not in use, and then run it.
   ```bash
   java -jar build/libs/bigtablet-homepage-server-1.3.0.jar --server.port=[port number]
   ```
  
