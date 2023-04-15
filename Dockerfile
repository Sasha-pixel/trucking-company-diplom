FROM openjdk:17
ARG JAR_ARTIFACT
COPY target/${JAR_ARTIFACT}.jar /app/notifications.jar
WORKDIR /app
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=${SPRING_PROFILE} notifications.jar"]
