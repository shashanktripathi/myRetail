#!/bin/sh
mongoimport --db myRetail --collection product_price --file product_price.json
mvn clean install
mvn spring-boot:run