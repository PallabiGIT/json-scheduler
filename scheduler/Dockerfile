FROM jelastic/maven:3.9.5-openjdk-21 AS build

WORKDIR /app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

#FROM adoptopenjdk/openjdk11
FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl

LABEL maintainer="swaroop.sp@gmail.com"
ENV LOGGER_FILE_LOCATION="/home/strategy-logs-folder"
COPY --from=build /home/app/target/scheduler-*.jar /usr/local/lib/scheduler.jar

CMD java $JVM_OPTIONS -jar /usr/local/lib/scheduler.jar