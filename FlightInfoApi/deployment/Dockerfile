FROM openjdk:13-jdk-alpine

ADD . /FlightInfoApi/
ADD ./deployment /deployment/

EXPOSE      8080

WORKDIR     /deployment

CMD ./build_and_deploy.sh