# MyRetail Case Study
myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps.
The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller.

# How to run the application

## Pre-requisites
1. Java 8
2. Maven
3. Mongodb

## Setup
This application when compiled can be executed via simple command:
```
./setup.sh
```

1. This command will seed product price data into local mongodb
2. Then will run the app on port 8080
3. API can be accessed via `localhost:8080`

## Result
1. Postman screenshots are available in the `screenshots` directory
2. Postman collection: https://www.getpostman.com/collections/b7e137c8eeb7591bd7e3