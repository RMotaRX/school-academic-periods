# SUITES MICROSERVICE

This micro service is responsible for managing all the operations regarding school academic periods for the Educacional Project.

## Purpose

This micro service does the following: 
* Create School Academic Period
* Edit School Academic Period
* Delete School Academic Period
* List School Academic Periods
* Query School Academic Period


## Development Guidelines

### Packaging
___

- **com.educacional.com.educacional.applicationsms.application.com.educacional.applicationsms.application**: Contains all spring boot related classes, inbounds and outbounds connections and Rest Configurations


- **com.educacional.com.educacional.applicationsms.application.com.educacional.applicationsms.domain**: Contains all abstractions
  responsible for delivering business value, i.e.: business entities, value-objects,
  business services, exceptions. The entities are currently depending on spring.

- **com.educacional.com.educacional.applicationsms.application.com.educacional.applicationsms.infra**: Carries all implementations
  of interfaces defined in the com.educacional.applicationsms.domain level and that should come from the host
  infrastructure itself, i.e.: configurations, database access,
  logging implementations, tracing.

### Artifact naming
___

Some common words are used throughout the source code to name classes and interfaces.
Each one is used to identify specific constructions. They are described below:

Suffixes:

- Client: Used to identify "Open Feign" interfaces.
- Controller: Contain http rest implementations
- Repository: Used to identify interfaces to data storages.
- Service: Used to identify business logic classes
- Request: Pojo Objects that are received in http requests
- Response: Pojo Objects that will be returned to the client in http requests
- Mapper: Classes that contain object mapper logic

Prefixes:

- MongoDB: Used to identify implementations relative to MongoDB.

## Configuration
___

This section describes all variables used by the service:

- MONGO_HOST: Any valid host name or IP address that points to a mongodb
- MONGO_PORT: Any valid port number that will be used by the mongodb
- MONGO_DATABASE: Name of the mongodb database
- MONGO_USERNAME: Username credential to connect to the mongodb
- MONGO_PASSWORD: Password credential to connect to the mongodb

DO NOT commit your local configuration files.

## Running
___

**Get the project**

Use the git client of your preference to clone the repository. Here is a command
line example:

```bash
$ git clone git@bitbucket.org:acmecorporation/sample-service.git
```

**Building the project**

Make sure you have maven installed and invoke the following command:

```bash
$ cd sample-service
$ mvn clean install
```

**Using java directly**

Once you have built the com.educacional.applicationsms.application, you may invoke it directly using java as
illustrated below:

```bash
$ java -jar target/sample-project-1.0.0-SNAPSHOT.jar
```
