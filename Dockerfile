FROM openjdk:11
ARG version
COPY target/product-rnd-2.5.13.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar","app.jar"]