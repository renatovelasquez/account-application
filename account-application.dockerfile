FROM amazoncorretto:17-alpine-jdk
RUN mkdir -p /app
WORKDIR /app
COPY backend/build/libs/*.jar ./app.jar
EXPOSE $PORT
CMD [ "java", "-jar", "./app.jar" ]