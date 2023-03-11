FROM adoptopenjdk/openjdk11
ENV TZ=Asia/Seoul
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.jar"]

