- Start up a PACT broker using a PostgreSQL
docker compose up -d

- Shutdown PACT broker
docker-compose down

Open browser
http://localhost:9292/

For using in the projects
mvn test
mvn pact:publish
mvn pact:verify

REST API
========
curl -X POST http://localhost:8080/create?state=NoUsers \
-d '{ "provider": { "name": "Animal_Service"} }'

-Response
{"port": 20008}

curl -X POST http://localhost:8080/complete -d '{ "port": 20008}'

curl -X POST http://localhost:8080/publish -d '{ "consumer": "Zoo", "consumerVersion": "0.0.1", "provider": "Animal_Service" }'

--Diagnostics
curl -X GET http://localhost:8080