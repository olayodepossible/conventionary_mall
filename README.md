

How to run the architecture:


1. Run all the Microservices in any IDE of your choice. And open Eureka in browser to monitor the running status of each services: http://localhost:8761

2. Create Mongo DB (MongoDb compass) for each Microservices (5):
	productServiceDB, customerServiceDB, shoppingServiceCommandDB, shoppingServiceQueryDB, orderServiceDB and vendorServiceDB

3. Install Kafka from the internet and navigate to the folder:
	How to start Kafka

	i. Go to terminal and navigate to kafka_2.13 folder

	ii. Start Zookeeper (for managing clusters in kafka) by running:
		sh bin/zookeeper-server-start.sh config/zookeeper.properties

	iii. Start kafka by typing this in another terminal:

		sh bin/kafka-server-start.sh config/server.properties

4. Download zipkin for service request tracing

- Then see the entire architecture on the browser:  http://localhost:9411/zipkin 

5. Test the api's from Client Application, or postman.

	
