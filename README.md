# Simple API Restful Server & AngularsJS Client

Deployment with [Docker](https://www.docker.com).

* Server Restful
* Websocket
* AngularsJS Client

## API Restful

Java project using [Jersey](https://jersey.java.net), [Grizzly](https://grizzly.java.net) and [Hibernate](http://hibernate.org)

#### Authentication
HEADER application/json

| URI path       | Resource class           | HTTP methods | Notes                                                                                                |
|----------------|--------------------------|--------------|------------------------------------------------------------------------------------------------------|
| /auth/register | AuthenticationController | POST         | {     "nickname": "string",     "address": "string",     "password": "string",     "role": integer } |
| /auth/login    | AuthenticationController | POST         | {    "nickname": "string",    "password":"string"}                                                   |


#### Users
HEADER application/json

HEADER authorization

authorization token is given with the `auth/login` action.

| URI path    | Resource class  | HTTP methods | Notes                                       |
|-------------|-----------------|--------------|---------------------------------------------|
| /users      | UsersController | GET          | Get all users.                              |
| /users/{id} | UsersController | GET          | Get a specific user depending its ID.       |
| /user/{id}  | UsersController | PUT          | {"nickname": "string", "address": "string"} |
| /users/{id} | UsersController | DELETE       | Delete a specific user depending its ID.    |


#### Channels

HEADER application/json

HEADER authorization

authorization token is given with the `auth/login` action.

| URI path   | Resource class     | HTTP methods | Notes                                    |
|------------|--------------------|--------------|------------------------------------------|
| /channels  | ChannelsController | GET          | Get all channels.                        |
| /channels | ChannelsController | POST         | {"name": "string", "user_id": "integer"} |

## Installation

### With Docker

`docker-compose up -d`


Then run the script shell `run.sh` allowing to change the ip into the **client site** to access to server container from the host.

 `./run.sh $(docker-machine ip)` or `./run.sh [your docker bridge ip]`

Now you can test the server access by using `curl`

```
curl -H 'Content-Type: application/json' \
     -X POST -d '{"nickname": "YMonnier", "address": "2253–2331 Alder St, Vancouver BC, Canada", "password": "abcd123456", "role": 0}' \
     $(docker-machine ip):8080/littleapp/auth/register

Response:
{
  "id": 1,
  "nickname": "ymonnier",
  "password": "$2a$10$ZdU9nA9.efNEFe7X4tjpL.0VA2I5JRdvXi7/DfQMMf4tk1HXvHLOK",
  "address": "10 street...",
  "token": null,
  "channels": [],
  "role": 0
}
```

or you can use the client angular...

Run the client: `cd web/ && npm install && bower install && grunt serve` or execute `web/dist/index.html`


Contributor
------------
* [@YMonnier](https://github.com/YMonnier)

License
-------
docker-restful-java is available under the MIT license. See the [LICENSE](https://github.com/YMonnier/docker-restful-java/blob/master/LICENSE) file for more info.
