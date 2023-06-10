#!/bin/bash

echo "Creating app on docker."

docker build -t digital-account-app .
docker-compose up -d

#.\gradlew.bat clean
#.\gradlew.bat build