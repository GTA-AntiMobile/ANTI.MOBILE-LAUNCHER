# Build stage: compile and package
FROM yannoff/maven:3.8-openjdk-19 AS build
WORKDIR /app

# Copy everything including your external JAR
COPY . .
COPY ANTI.MOBILE-LAUNCHER-Anti-Mobile_Launcher.jar .

# Install custom dependency into local Maven repo
RUN mvn install:install-file \
    -Dfile=ANTI.MOBILE-LAUNCHER-Anti-Mobile_Launcher.jar \
    -DgroupId=com.samp \
    -DartifactId=LauncherTest \
    -Dversion=1.0-SNAPSHOT \
    -Dpackaging=jar

# Now package your app
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:19-slim

RUN apt-get update
RUN apt-get install -y libfreetype6 fontconfig
RUN rm -rf /var/lib/apt/lists/*
RUN apt-get update && apt-get install -y xvfb

WORKDIR /app

# Copy your built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
CMD ["xvfb-run", "java", "-jar", "app.jar"]
