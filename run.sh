#!/usr/bin/env bash

if [ "$(docker ps -a | grep subscription-mysql)" ]; then
    docker start subscription-mysql
else
    docker run --name subscription-mysql -p 3308:3306 -e MYSQL_ROOT_PASSWORD=secret-pass -e MYSQL_USER=subscription -e MYSQL_PASSWORD=subscription -e MYSQL_DATABASE=subscription -d mysql:latest
fi

if [ "$(docker ps -a | grep subscription-mysql)" ]; then
    docker start subscription-service
else
    # Generate the application's jar file
    ./gradlew build

    # Build the docker image using the Dockerfile placed in this folder
    docker build -t subscription-service:1 --build-arg jar_file=./build/libs/subscription-service-1.0.0.jar .
    docker run --name subscription-service --link subscription-mysql -p 8080:8080 subscription-service:1
fi
