FROM gradle:7.3.3-jdk17

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:latest

EXPOSE 6001

COPY --from=build build/libs/storage-api-1.0.0.jar storage-api.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","storage-api.jar"]
