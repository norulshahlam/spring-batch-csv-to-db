### create schema and table
 
		CREATE TABLE `login_app`.`product` (
		`id` INT NULL,
		`name` VARCHAR(45) NULL,
		`description` VARCHAR(45) NULL,
		`price` DECIMAL(9,2) NULL);
		)
 
-  model - product.java
-  csv file
- batchconfig.java - read, process, write
- reading are done
- process
- writer
- configure datasource
- step
- job
- test to run
- @EnableBatchProcessing in main method

### Diagram illustration
![Image](./src/main/resources/spring%20batch.JPG)

 
ref: 
https://livebook.manning.com/book/spring-batch-in-action/chapter-5/60
https://docs.spring.io/spring-batch/docs/1.0.x/spring-batch-docs/reference/html/spring-batch-infrastructure.html