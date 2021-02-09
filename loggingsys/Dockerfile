FROM maven:3-jdk-8 as builder
WORKDIR /project
COPY entities/ entities
WORKDIR /project/entities
RUN mvn install
WORKDIR /project
#COPY products/ .
ADD ./loggingsys .
RUN mvn package

FROM java:8-alpine
WORKDIR /app
COPY --from=builder /project/target/loggingsys.jar ./loggingsys.jar
#ENTRYPOINT ["/bin/sh", "-c"]
CMD java -jar loggingsys.jar