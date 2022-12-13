# contract-testing

This project is a PoC to check the different technologies related with the contract testing in the microservices scope.

Service A (inventory)
Service B (pricing)
Service C (offers)

A -> B -> C

[GET] /inventory
{
"productId" : 100,
 "desc" : "tomatoes"
 "price" : 2
},
{"productId" : 101,
"desc" : "potatoes"
"price" : 3
}

- PACT
- Spring Cloud Contract

- [Install PACT Server Broker](./pact-broker/README.md)

  * Start PACT broker
    - docker compose -f pact-broker/docker-compose.yml up -d 
    - open http://localhost:9292/ in the browser

  * Start services B and C
    - cd c-service && mvn spring-boot:run
    - cd b-service && mvn spring-boot:run

  * Consumer test A service
    - cd a-service
    - mvn clean test pact:publish
    
  * Consumer and provider test B service
    - cd ../b-service
    - mvn clean test pact:publish
    
  *  Verify provider test B service
    - cd ../b-service
    - mvn pact:verify -Dpact.verifier.publishResults=true

  *  Provider test C service and verify
    - cd ../c-service
    - mvn clean test pact:verify -Dpact.verifier.publishResults=true

## Links:
  
  - https://docs.pact.io/implementation_guides/jvm/pact-jvm-server#building-a-distribution-bundle
  - https://docs.pact.io/pact_broker
