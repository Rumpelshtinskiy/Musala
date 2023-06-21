# Musala Task
## Description
There is a major new technology that is destined to be a disruptive force in the field of transportation: the drone.
Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

## Requirements
* Java 17
* Docker
* Docker-compose
* Postman (optional)

## Launching the application
The easiest way to start is to use docker-compose from the root directory of the project:
```shell
docker-compose up -d
```
The application will be available at `localhost:8080`

## Usage
For testing, you can use `Postman` or any other similar tool.

POST http://localhost:8080/api/drone/register

example body:
```json
{
  "model": "HEAVYWEIGHT",
  "weightLimit": 500
}
```
Also model could be: **LIGHTWEIGHT**, **MIDDLEWEIGHT**, **CRUISERWEIGHT** or **HEAVYWEIGHT**

POST http://localhost:8080/api/drone/{id}/loading

example body:
```json
{
  "name": "CALPOL",
  "count": 5
}
```

POST http://localhost:8080/api/medication/register

example body:
```json
{
  "name": "CALPOL",
  "weight": 30,
  "code": "FR34"
}
```
POST http://localhost:8080/api/medication/{id}/image/upload

form-data:
```
  Key:"image" 
  File as value: image.jpg
```

GET http://localhost:8080/api/drone/{id}/loaded/medication

GET http://localhost:8080/api/drone/available

GET http://localhost:8080/api/drone/{id}/battery

GET http://localhost:8080/api/medication/{id}/image/download

