############################################################
##                   BUILD MAVEN                          ##
############################################################

FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /build

COPY ms-user/pom.xml ./ms-user/pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -pl ms-user -am dependency:go-offline

COPY ms-user/src ./ms-user/src

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -pl ms-user -am clean package -DskipTests

    
############################################################
##                   IMAGE DOCKER                         ##
############################################################

FROM eclipse-temurin:25-jre

RUN apt-get update \
&& apt-get install -y --no-install-recommends wget \
&& rm -rf /var/lib/apt/lists/*

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app

COPY --from=build /build/ms-user/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=default
EXPOSE 8081
USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]