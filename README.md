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
2. Postman collection:
```json
{
	"info": {
		"_postman_id": "5034b435-5d16-4298-8d5e-bc4138f41703",
		"name": "myRetail",
		"schema": "https://schema.getpostman.com/json/collection/v2.0.0/collection.json"
	},
	"item": [
		{
			"name": "Get Product",
			"request": {
				"method": "GET",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": ""
				},
				"url": "localhost:8080/products/13860428",
				"description": "Get Product"
			},
			"response": []
		},
		{
			"name": "Update Product Price",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"name": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"id\": 1386042,\n    \"name\": \"The Big Lebowski (Blu-ray)\",\n    \"current_price\": {\n        \"value\": \"18.66\",\n        \"currency_code\": \"USD\"\n    }\n}"
				},
				"url": "localhost:8080/products/1386042",
				"description": "Update Product Price"
			},
			"response": []
		}
	]
}
```