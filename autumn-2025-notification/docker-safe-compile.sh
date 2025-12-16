docker run --rm ^
  -v "%cd%":/app ^
  -w /app ^
  maven:3.8.6-openjdk-11 ^
  mvn clean package -DskipTests