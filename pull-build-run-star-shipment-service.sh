#!/bin/bash

export PATH=$PATH:/opt/gradle/gradle-6.7/bin

cd /var/www/java-modules/star-be-java-library
git pull
gradle clean fatJar
cp ./build/libs/*.jar /var/www/java-modules/star-be-shipment-service/libs/

cd /var/www/java-modules/star-be-shipment-service/
git pull
gradle clean build -x test

./restart-star-shipment-service.sh