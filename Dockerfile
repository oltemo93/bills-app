FROM openjdk:17-jdk-slim
RUN mkdir -p /app
COPY target/bill-0.0.1-SNAPSHOT.jar /app/app.jar
RUN chmod 755 /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar", "--spring.profiles.active=dev"]