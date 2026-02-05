FROM eclipse-temurin:25-jre-alpine
# To render fonts
RUN apk add --no-cache fontconfig ttf-dejavu
WORKDIR /app

COPY ./build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.awt.headless=true", "-jar", "app.jar"]