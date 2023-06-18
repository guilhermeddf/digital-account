#!/bin/bash


echo "Starting application."

#sh remove-docker-images.sh
sh build-application.sh
sh create-docker-image.sh

cd ..
docker-compose up -d
