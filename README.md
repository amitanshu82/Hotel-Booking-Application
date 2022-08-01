# Hotel-Booking-Application

This application will have these microservices-
1.	Booking Service
2.	Payment Service
3.	Notification Service
4.	Config Server 
5.	Eureka Server

1. Booking Service- This microservice has two POST end points, /booking a /booking/{bookingId}/transaction. These two end points have been defined in the BookingController class. This Service also consist of BookingService and BookingServiceImpl class which contains the code relate to booking service. 
/booking end-point- This endpoint takes the BookingRequest POJO as the request body parameter and create and save booking in DB and then returns BookingInfoDto as response.
/booking/{bookingId}/transaction – This API takes TransactionRequest POJO as the request body and calls the Payment Service to save and get transaction Id corresponding to that booking and the it raise the Kafka event to topic “message” using Kafka Producer (getting created by @Bean inside KafkaConfig class).

2.  Payment Service – This Service is used for doing the payment and it has two APIs one POST and another GET. The POST API is getting called by the Banking Service and it takes TarnsactionRequest as the body parameter. The POST API calls the PaymentService which use PaymentRepository (JPA repository) to save transaction in DB.
Payment service also uses Config-Server for getting the configuration.

Payment Service is also registered with Eureka server with the name as PAYMENT-SERVICE and configuration is defined in application.yml 

3. Notification Service- This service acts as Kafka Consumer .This one is not a spring boot but a maven project and contains only one class Consumer. Inside main method of consumer class all logic for consuming the message has been written. After consuming the message from topic “message” this service display the message in console output.

4. Eureka - This service is Eureka server which holds information about both the Banking Service and Payment service. I have not used this one for service discovery . This one has been used for checking status of running instnaces of BOOKING-SERVICE and PAYMENT-SERVICE 

5. Config-Server – This is configuration server which is getting used to provide configuration to both BOOKING SERVICE and PAYMENT SERVICE. This service is using my gut Repo for storing the configurations.



To run this entire application – 
1. First download the Hotel-Booking-Application. 
2. Go to the git url - https://github.com/amitanshu82/git-config-repo and change the configuration for BOOKING-SERVICE.properties file and PAYMENT-SERVICE.properties file . In these files provide the DB connection credentials and also Kafka cluster connection (Kafka Bootstrap Server ) and Other details.
3. After this step first start the and run the Config-Server
4. After this start the Eureka server and then start Booking Service and Payment Service
5. At last run the Notification service .
