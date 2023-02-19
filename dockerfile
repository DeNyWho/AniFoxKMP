FROM openjdk:18
COPY ./backend/build/libs/* ./app.jar
CMD ["java","-jar","app.jar"]