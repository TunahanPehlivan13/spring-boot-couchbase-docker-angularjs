FROM java:8
VOLUME /tmp
ADD target/todo-app-1.0.0.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]