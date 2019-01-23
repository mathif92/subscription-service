#!/usr/bin/env bash
if [ "$(docker ps -a | grep subscription-mysql)" ]; then
    docker start subscription-mysql
else
    docker run --name subscription-mysql -p 3308:3306 -e MYSQL_ROOT_PASSWORD=secret-pass -e MYSQL_USER=subscription -e MYSQL_PASSWORD=subscription -e MYSQL_DATABASE=subscription -d mysql:latest
fi