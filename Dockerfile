FROM openjdk:alpine

EXPOSE 80
RUN apk add --no-cache git
RUN apk add --no-cache maven
RUN git clone https://github.com/Lorismelik/JSON_validator.git
WORKDIR /JSON_validator
RUN mvn clean install -e
CMD mvn exec:java -e