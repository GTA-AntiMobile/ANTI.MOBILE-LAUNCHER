# Build stage: compile and package
FROM yannoff/maven:3.8-openjdk-19 AS build
WORKDIR /app

# Copy all project files to Docker
COPY . .

# Install custom dependency into local Maven repo
RUN mvn install:install-file \
    -Dfile=target/ANTI.MOBILE-LAUNCHER-Anti-Mobile_Launcher.jar \
    -DgroupId=com.samp \
    -DartifactId=LauncherTest \
    -Dversion=1.0-SNAPSHOT \
    -Dpackaging=jar

# Now package your app
RUN mvn clean package -DskipTests

# Replace with your actual JAR name after build
CMD ["java", "-jar", "target/ANTI.MOBILE-LAUNCHER-Anti-Mobile_Launcher.jar"]
