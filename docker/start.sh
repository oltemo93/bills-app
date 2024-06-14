#!/bin/bash
set -xe

#package the spring app
../mvnw clean package

#spin up all the services
docker compose up --build bill-app
