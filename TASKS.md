1. Add rest assure tests for CustomerController along with authenticated user
2. Add rest assure tests for BackupJSONController  along with authenticated user
3. Make sure end to end test works
4. Write unit tests for all the services
5. Write unit tests for all listeners
6. Write a new API
   @RequestMapping(value = "/customer/{orderId}", method = RequestMethod.GET) 

which return XML response instead of JSON
7. 
a. Create a design and review the design with Architect. Ref- https://phauer.com/2015/restful-api-design-best-practices/
b .Create a new controller in scheduler-api which connects to a random API return the response.
c. Create tests which mocks the random API using wire mock. 
d. Design to contain - A Flow diagram, sequence diagram, SWAGGER Contract
e. Use https://www.lucidchart.com/ for design
f. 2 design documents needed - 1. API design 2. Testing design

Random API - Ex - https://api.apis.guru/v2/list.json
Reference - https://www.geeksforgeeks.org/how-to-call-or-consume-external-api-in-spring-boot/

