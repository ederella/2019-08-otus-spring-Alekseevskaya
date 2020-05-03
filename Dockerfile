FROM maven:3.6.2-jdk-8
ENV PROJECT_DIR=/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve
ADD ./src/ $PROJECT_DIR/src
RUN mvn clean install -Dmaven.test.skip

FROM openjdk:8-jdk
ENV PROJECT_DIR=/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/Library* $PROJECT_DIR/
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.data.mongodb.uri=mongodb://library_db:27017/library_db", "/project/Library-0.0.1-SNAPSHOT.jar"]
