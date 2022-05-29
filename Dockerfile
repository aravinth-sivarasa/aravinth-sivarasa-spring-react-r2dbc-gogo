FROM openjdk:11
ARG version
COPY target/product-rnd-${version}.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar","app.jar"]