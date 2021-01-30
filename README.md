# springboot-rest-prime
A RESTful service which calculates and returns all the prime numbers up to and including a number provided.

Example:
The REST call looks like http://your.host.com/primes/10 and returns JSON or XML content based on the Accept Header (Accept:application/json) provided:
```
{
  "Initial":  10,
  "Primes": [2,3,5,7]
}
```

## Tech Stack
- JDK 1.8
- Maven
- Spring MVC
- Spring Caching 
- Junit 5 / Mockito
- Google App Engine

## How to build the application
```
mvn clean install
```
## Accepted Request
```
GET /prime/47
HOST <server host>
Content-Type: application/json (Application can consume ALL or empty content-type)
algorithm-type: naive (Optional header to identify algorithm. Default value is sieve)
Accept: application/json (Application can produce JSON and XML response)
```
## Error Handeller
#### Invalid Algorithm type Request:
```
GET /prime/22211
algorithm-type: invalid
Accept: application/json
```
#### Response:
```
Response:
{
    "errorCode": "INVALID.ALGORITHM.FOUND",
    "errorMessage": "No algorithm available for algo type : naive1"
}
```

#### Invalid Input Range Request: 
```
GET /prime/-100
algorithm-type: invalid
Accept: application/json
```
#### Response:
```
Response:
{
    "errorCode": "NON.NATURAL.NUMBER",
    "errorMessage": "Number range should be positive."
}
```
## Access the application 
- http://localhost:8080/prime/13
