# SqlGenFromLogger
* Tested with "org.hibernate.orm" version "6.5.2.Final"
* If you need to form a sql query from a log file by substituting parameters into it,
* then you can form a query with arguments.
*
* To do this:
* 1. Enable debugging by adding environment variables SPRING_JPA_SHOW_SQL = 'true' and LOGGING_LEVEL_ORG_HIBERNATE_ORM = 'trace'
* or in the application.yml file
* logging:
  * level:
    * org.hibernate.orm.jdbc.bind: trace
* jpa:
  * hibernate:
    * show-sql: true
*
* 2. After executing the SQL query, the query should get into the log file, then it must be saved
* to a separate file part in the format below
*
* File format
Hibernate: select je1_0.id from joke je1_0 where je1_0.hash_code=? fetch first ? rows only
2024-12-10T19:14:34.525 [scheduling-1] TRACE org.hibernate.orm.jdbc.bind - binding parameter (1:INTEGER) <- [718258863]
2024-12-10T19:14:34.525 [scheduling-1] TRACE org.hibernate.orm.jdbc.bind - binding parameter (2:INTEGER) <- [1]