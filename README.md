# jOOQ And SpringBoot demo project

If you are looking how to combine SpringBoot & Postgres & Flyway & Jooq then you are on the right place.  
The most difficult part of usage of all these technologies is that:
1. You need to run migration before you run your application in dev/stage/prod.
2. Jooq generates classes from the database.
3. If not all migrations applied to the database then you can't generate jooq classes.

Solution is here.

### Run the Database

   `docker run -p 5432:5432 --name demojooq -e POSTGRES_PASSWORD=mysecretpassword -d postgres:15.0`

### Run the Build with jooqCodegen

Before you run you need to generate jooq classes.  
The way its done is the following:  
1. Run Postgres 
2. Execute gradle command:  
   
   `gradle jooqCodegen`  

   It will migrate all your scripts from resources/db/migration with flyway. And then it will generate jooq DB classes.

3. You will see all the generated code under src/main/java/org/example/springbootjooq/generated
   It is up to you  where to store generated files(change at build.gradle). I chose this location instead of /build for visibility.
4. Run project as usual from IDE or jar


Inspired by: https://github.com/aabarmin/ab-examples/tree/main/jooq