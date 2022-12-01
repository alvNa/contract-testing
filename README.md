# contract-testing

This project is a PoC to check the different technologies related with the contract testing in the microservices scope.

Service A (inventory)
Service B (pricing)
Service C (offers)

A -> B -> C

[GET] /inventory
{"productId" : 100,
 "desc" : "tomatoes"
 "price" : 2
},
{"productId" : 101,
"desc" : "potatoes"
"price" : 3
}

- PACT
- Cucumber
- Spring Cloud Contract

- PACT Server (Broker)
Install with docker
docker run -d -p 8080:8080 -p 20000-20010:20000-20010 uglyog/pact-jvm-server

curl -X POST http://localhost:8080/create?state=NoUsers \
-d '{ "provider": { "name": "Animal_Service"} }'

-Response
{"port": 20008}

curl -X POST http://localhost:8080/complete -d '{ "port": 20008}'

curl -X POST http://localhost:8080/publish -d '{ "consumer": "Zoo", "consumerVersion": "0.0.1", "provider": "Animal_Service" }'

--Diagnostics
curl -X GET http://localhost:8080

## Links:
  
  - https://docs.pact.io/implementation_guides/jvm/pact-jvm-server#building-a-distribution-bundle
  - https://docs.pact.io/pact_broker
  - 