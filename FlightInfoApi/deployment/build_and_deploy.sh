#!/bin/sh

if [ ! -f ./flightinfoapi.jar ]
then
  echo "Installing maven"
  cd /opt/
  wget http://www-eu.apache.org/dist/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
  tar -xvzf apache-maven-3.0.5-bin.tar.gz
  mv apache-maven-3.0.5 maven
  export M2_HOME=/opt/maven
  export PATH=${M2_HOME}/bin:${PATH}
  cd ../FlightInfoApi
  echo "Building..."
  mvn clean install
  echo "moving jar file to deployment "
  cp target/flightinfoapi.jar /deployment
  mv deployment/ ../
  echo "Cleaning code sources..."
  cd ..
  rm -rf FlightInfoApi/
  cd deployment
fi

echo "Deployment..."
java -jar flightinfoapi.jar
