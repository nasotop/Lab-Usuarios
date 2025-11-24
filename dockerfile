FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY target/*.jar app.jar
COPY Wallet_UJOGB6W5ZU3CB1IO /wallet

ENV TNS_ADMIN=/wallet
EXPOSE 8080
ENTRYPOINT java -jar app.jar
