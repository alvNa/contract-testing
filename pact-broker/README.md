- Start up a PACT broker using a PostgreSQL
docker compose up -d

- Shutdown PACT brocker
docker-compose down

Open browser
http://localhost:9292/

For using in the projects
mvn test
mvn pact:publish
mvn pact:verify