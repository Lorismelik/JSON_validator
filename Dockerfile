FROM maven:3.3-jdk-8-onbuild
CMD ["java","-jar","target/json_validator-1.0-jar-with-dependencies.jar"]
EXPOSE 80
