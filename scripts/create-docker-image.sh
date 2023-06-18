#!/bin/bash

echo "Creating docker application image."
cd ..
docker build -t digital-account-app .