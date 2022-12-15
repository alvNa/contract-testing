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

PACT BROKER CLI
===============

export PACT_BROKER_BASE_URL="http://localhost:9292"
export PACT_BROKER_USERNAME=""
export PACT_BROKER_PASSWORD=""

-- Env list command
docker run --rm pactfoundation/pact-cli:latest \
pact-broker list-environments -b=$PACT_BROKER_BASE_URL

-- Version list command
docker run --rm  pactfoundation/pact-cli:latest  \
pact-broker list-latest-pact-versions -b=$PACT_BROKER_BASE_URL

-- Without env
pact-broker can-i-deploy -b="http://localhost:9292" --pacticipant a-service --version 0.1.0-SNAPSHOT

-- With test env
docker run --rm pactfoundation/pact-cli:latest \
pact-broker can-i-deploy -b="http://localhost:9292" --pacticipant a-service --latest --to-environment test

-- Specifying all the "pacticipants"
pact-broker can-i-deploy -b="http://localhost:9292" --pacticipant a-service --version 0.1.0-SNAPSHOT \
--pacticipant b-service --version 0.1.0-SNAPSHOT \
--pacticipant c-service --version 0.1.0-SNAPSHOT --to-environment test

-- Check the environment by UUID
pact-broker describe-environment --uuid=049d5a59-5f49-4c7b-aeef-f95577900cd5 -b=$PACT_BROKER_BASE_URL

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

* Links
- https://docs.pact.io/pact_broker/client_cli/readme