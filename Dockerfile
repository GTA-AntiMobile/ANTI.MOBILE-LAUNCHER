# -----------------------------
# Build stage
# -----------------------------
FROM yannoff/maven:3.8-openjdk-19 AS build

WORKDIR /app

# Copy full source and dependencies
COPY . .

# Copy the external JAR into lib/
RUN mkdir -p lib && cp ANTI.MOBILE-LAUNCHER-Anti-Mobile_Launcher.jar lib/

# Build the app (no need for install-file now)
RUN mvn clean package -DskipTests

# -----------------------------
# Runtime stage
# -----------------------------
FROM openjdk:19-slim

# Install fonts + headless display
RUN apt-get update && \
    apt-get install -y libfreetype6 fontconfig xvfb && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy only the packaged app
COPY --from=build /app/target/*.jar app.jar

CMD ["xvfb-run", "java", "-jar", "app.jar"]
