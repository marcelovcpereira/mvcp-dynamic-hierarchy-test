# Personio Dynamic Hierarchy Test
Implementation and automation of a Dynamic Hierarchy processor in Kotlin.

## Download
```bash
git clone https://github.com/marcelovcpereira/mvcp-personio-test.git
```

## Building, Testing And Running locally
Pre requisites:
- `Git`
- `Java 8 - SDK`
- `Gradle`


#### Building And Unit Testing
Run inside project folder root:
```bash
gradle build
``` 
A jar file will be generated at /build/libs folder

#### Running Locally
```bash
java -jar mvcp-personio-test-1.0.0-SNAPSHOT.jar
``` 
This will run the server locally, listening for HTTP requests coming on port 8080.


## No-install Run: From Docker hub
Pre requisites:
- `Docker`

Starting service:
```bash
docker run --rm --name mvcp-personio-test -p 8080:8080 marcelovcpereira/personio-test:1.0.0 
```
Stopping service:
```bash
docker stop mvcp-personio-test
```

## Internal Components
**Entrypoint:**
Central controller responsible for intercepting all http requests done to the app.

**DynamicHierarchy:**
The Dynamic Hierarchy is the heart of the application and is responsible for delegating the creation of a Hierarchy, 
exhibition of a Hierarchy and for retrieving an Employee's Supervisors inside a Hierarchy

**Employee Factory:**
Component responsible for creating valid Employees objects based on input

**Employee Manager:**
Component responsible for managing persistency on Employees

**Input Validator:**
Component responsible for applying validation strategies on the input

# Improvements:
- Implement tests
- Add a validation for checking double-assignments on Input (not allowed)
- Improve authentication mechanism (currently Basic HTTP)
- Externalize some configuration (admin user/pass could be in properties, port binding could be in properties, etc)
- Allow multiple hierarchies to be created and persisted 