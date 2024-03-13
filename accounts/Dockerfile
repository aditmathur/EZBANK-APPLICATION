FROM amazoncorretto:21-alpine3.19

MAINTAINER "Adit Mathur"

# Add Application's jar to the image
COPY build/libs/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Start the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]

