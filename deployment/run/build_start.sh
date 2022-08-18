#!/bin/bash

set -e

echo "************Building project************"
./gradlew clean build


echo "************Building Image************"
./deployment/docker/build-image-server-app.sh

echo "************Starting docker ************"

./deployment/run/docker-container-app-start.sh