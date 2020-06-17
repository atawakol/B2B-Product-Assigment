# B2B Product Management

This application performs basic operations to handle the products life cycle.
The Rest Services support the following

  - Create a new product
  - Get a list of all products
  - Get details about a product
  - Able to update a product
  - Able to set price points for different currencies for a products

### Tech

* [Jdk 1.8] - Java 8 was used to test this application. This is the only prerequesties to install and test this application
* [SPring boot 2] - Spring 2 was used as base framework for rest endpoint
* [Gradle] - Gradle was used a build tool
* [MYSQL] - The database engine that was used for testing of this application

### Installation
-- The application requires JDK 1.8 to exist on the machine as well as MySQL DB. 
-- It runs by default using the mysql and the database has to be created before starting the application. The database is assumed to be called "productDB". 

-- To change the db configuration ( DB Name, username, password), you can open the `application.properties` file and set your own configurations. 
-- Please Note that the database will be recreated with each run by the property `spring.jpa.hibernate.ddl-auto = create-drop`. If you want to persist the DB between run, set this property to none 

-- This application was also tested against in memory db H2. That DB is used for all unit tests.
-- You can use the gradlew batch file to download the latest version of gradle.

### Build and run
 - Create the MySQL DB
 - Update `application.properties` configuration to match your DB
 - Open a CMD

```sh
$ cd B2B-Product-Assigment
$ gradle build
$ gradle bootRun
```

This will run the unit tests and run the application on port 8080.

### Development

 - This Spring application can be used with any Java IDE.
     - From your IDE, you can simply compile and run the main method from class ProductApplication.  
 - Also, You can use gradle to build the application and run the unit tests through gradle,
```sh
$ gradle build 
$ gradle testSuite
```
 - The unit test report file can be found in the path  
     - <project_root>/build/reports/tests/test    
     - <project_root>/build/reports/tests/testSuite    
     
 - Once you build the application with `gradle build`, you can run the generated jar from path '<Project_root>/build/libs' with command `java -jar product-0.0.1-SNAPSHOT.jar`



#### Usage
Use the following URLS with the curl to invoke the rest services:
 
 * Get a list of all products and all of its details : 
		`curl -H "Accept:application/json" http://localhost:8080/api/products/`
     
 * Get details about a product by id : 
		`curl -H "Accept:application/json" http://localhost:8080/api/products/[id]/`:  
		
 * Retrieve a list of products that match the description criteria :
		`curl -H "Accept:application/json" http://localhost:8080/api/products/search/findByDescription=[description]=desc`

 * Retrieve a list of products that match the title criteria :
		`curl -H "Accept:application/json" http://localhost:8080/api/products/search/findByTitle=[title]=some`
		 
 * Create new Product with name and description : 
		`curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/products/ -d '{"vendorUID": 1, title": "CURL Product", "description": "using CURL desc"}'` 
 
 * Create new Product with title, description, dietry flags
		`curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/products/ -d '{"vendorUID": 2, "title": "secotnd product product","dietaryFlags": [{"dietaryFlagName": "Plastic"},{"dietaryFlagName": "lactine"}]}'` 
  
 * Create new Product with JSON in the file :
		`curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/products/ -d "@[File_Name]"`
		
 *  Update the product with [id] to the JSON object : 
		`curl -i -X PUT -H "Content-Type:application/json" http://localhost:8080/products/[id]/ -d '{"title": "CURL Product Updated name", "description": "using CURL desc"}'`
		 
  * delete Product: 
		`curl -i -X DELETE -H "Content-Type:application/json" http://localhost:8080/products/[id]/`
 
----

 
#### Notes 
The structure of the FUll JSON product is as follow

```JSON

	{
    "productId": 2,
    "vendorUID": 2,
    "uuid": "742fa81c-5ca4-4e6a-b99b-9748ac2542b1",
    "title": "secotnd product product",
    "description": null,
    "price": 21.23,
    "imagePath": null,
    "numberOfViews": 0,
    "dietaryFlags": [
        {
            "dietaryId": 1,
            "dietaryFlagName": "Plastic"
        },
        {
            "dietaryId": 2,
            "dietaryFlagName": "lactine"
        }
    ]
}
```


 
 
 
   

