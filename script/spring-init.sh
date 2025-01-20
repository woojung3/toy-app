#!/bin/bash

# Create a basic Spring Boot project and run it 🚀
spring init \
    --java-version=21 \
    --boot-version=3.4.1 \
    --packaging=jar \
    --group-id=com.jwlee \
    --artifact-id=toy-app \
    --version=0.0.1-SNAPSHOT \
    --package-name=toy \
    --build=maven \
    --dependencies=devtools,lombok,modulith,htmx,thymeleaf,security,h2,data-jpa,web \
    --language=java \
    toy-app.zip &&
unzip toy-app.zip -d toy-app && rm toy-app.zip
cd toy-app && mvn spring-boot:run

# Additional Javascript dependencies
#		<dependency>
#			<groupId>org.webjars</groupId>
#			<artifactId>bootstrap</artifactId>
#			<version>5.3.3</version>
#		</dependency>
#        <!-- https://mvnrepository.com/artifact/org.webjars.npm/htmx.org -->
#        <dependency>
#            <groupId>org.webjars.npm</groupId>
#            <artifactId>htmx.org</artifactId>
#            <version>2.0.4</version>
#        </dependency>
#        <!-- https://mvnrepository.com/artifact/org.webjars.npm/alpinejs -->
#        <dependency>
#            <groupId>org.webjars.npm</groupId>
#            <artifactId>alpinejs</artifactId>
#            <version>3.14.8</version>
#        </dependency>
