#!/bin/bash
cd login && mvn spring-boot:run &
cd student-registration && mvn spring-boot:run &
wait