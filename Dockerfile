# Étape 1 - Builder avec cache Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline  # Télécharge les dépendances une fois
COPY src ./src
RUN mvn package -DskipTests

# Étape 2 - Image finale légère
FROM eclipse-temurin:17-jre-jammy  # JRE au lieu de JDK
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]