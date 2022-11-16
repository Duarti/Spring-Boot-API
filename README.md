This is a basic Spring Boot API with CRUD operations, authentication and authorization. It consists of users who can have their own posts and two roles: user or admin.

Used: Spring Security and JPA with mysql database.

-----------------------------------
Setting up the app :

At application.properties :
On spring.datasource.url=jdbc:mysql://localhost:3306/DATABASE_NAME, put your database name instead of DATABASE_NAME.

At

spring.datasource.username=USER,
spring.datasource.password=PASSWORD

change USER and PASSWORD to your database user and password respectively.

When starting the app for the first time, you might want to add an admin user to start with. You can do that by running a SQL query on import.sql file.
If you want to add some dummy data to start with, run every query on this file, to start with 3 users, and 2 posts. More detailed info on import.sql comments.

You can also create users from API endpoint : /api/register, or give admin privileges to specific users from endpoint : /api/user/{userId}/makeadmin.

You can log in on the endpoint /api/login by giving username and password credentials as a request body on the format x-www-form-urlencoded. It will return a response with JWT token for authorization.