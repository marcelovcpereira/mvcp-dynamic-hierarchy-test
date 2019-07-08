FROM java:openjdk-8-jdk-alpine
MAINTAINER Marcelo Pereira <marcelovcpereira@gmail.com>

RUN mkdir /scripts

COPY ./build/libs/mvcp-personio-test-1.0.0-SNAPSHOT.jar /scripts/mvcp-personio-test.jar
COPY ./docker-entrypoint.sh /scripts/docker-entrypoint.sh
RUN ["chmod", "+x", "/scripts/docker-entrypoint.sh"]

EXPOSE 8080

ENTRYPOINT ["/scripts/docker-entrypoint.sh"]