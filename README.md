# Simple API Restful Server & AngularsJS Client

Deployment with [Docker](https://www.docker.com).

## API Restful

Java project using [Jersey](https://jersey.java.net), [Grizzly](https://grizzly.java.net) and [Hibernate](http://hibernate.org)

| URI path      | Resource class   | HTTP methods   | Notes                                                                           |
|---------------|------------------|----------------|---------------------------------------------------------------------------------|
| /persons      | PersonController | GET, POST      | POST PARAM application/json {   "name": "My Name",    "address": "My Address" } |
| /persons/{id} | PersonController | GET, UPDATE, DELETE |                                                                                 |

## Installation

### Without Docker

1. `git clone git@github.com:YMonnier/docker-restful-java.git`
2. `cd docker-restful-java/`
3. Create a database and user/password on [PostgresSQL](https://www.postgresql.org)
4. Change **Hibernate configuration** depending on your database settings (`server/src/main/resources/hibernate.cfg.xml`)
5. Change the **host url** server (`server/src/main/java/com/ymonnier/restful/littleapp/Main.java`)
6. Change the api url on the client side(`$rootScope.apiURL web/app/scripts/app.js`)

Run the server:

`cd server/ && mvn package exec:java`

Run the client:
`cd web/ && npm install && bower install && grunt serve`

or

execute `web/dist/index.html`

### With Docker
```
docker-compose up -d
```
Then run the script shell `run.sh` allowing to change the ip to access to server container from the host.

 `./run.sh $(docker-machine ip)` or `./run.sh [your docker bridge ip]`

Now you can test the server access by using `curl`

```
curl -H 'Content-Type: application/json' \
     -X POST -d '{"name": "YMonnier", "address": "2253–2331 Alder St, Vancouver BC, Canada"}' \
     $(docker-machine ip):8080/littleapp/persons/

Response:
{
  "id": 1,
  "name": "YMonnier",
  "address": "2253–2331 Alder St, Vancouver BC, Canada"
}
```

or you can use the client angular to add/update/delete persons...

Run the client: `cd web/ && npm install && bower install && grunt serve` or execute `web/dist/index.html`


Contributor
------------
* [@YMonnier](https://github.com/YMonnier)

License
-------
docker-restful-java is available under the MIT license. See the [LICENSE](https://github.com/YMonnier/docker-restful-java/blob/master/LICENSE) file for more info.
