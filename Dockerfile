FROM openjdk:17.0.2 as JAR_EXTRACT
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
ARG JAR_FILE=*.jar
RUN cp ./target/${JAR_FILE} ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM openjdk:17.0.2
WORKDIR /application
COPY --from=JAR_EXTRACT /app/dependencies ./
COPY --from=JAR_EXTRACT /app/spring-boot-loader ./
COPY --from=JAR_EXTRACT /app/snapshot-dependencies ./
COPY --from=JAR_EXTRACT /app/application ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]