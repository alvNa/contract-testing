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

