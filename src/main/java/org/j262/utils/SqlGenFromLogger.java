package org.j262.utils;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* Tested with "org.hibernate.orm" version "6.5.2.Final"
* If you need to form a sql query from a log file by substituting parameters into it,
* then you can form a query with arguments.
*
* To do this:
* 1. Enable debugging by adding environment variables SPRING_JPA_SHOW_SQL = 'true' and LOGGING_LEVEL_ORG_HIBERNATE_ORM = 'trace'
* or in the application.yml file
* logging:
*   level:
*       org.hibernate.orm.jdbc.bind: trace
* jpa:
*   hibernate:
*       show-sql: true
*
* 2. After executing the SQL query, the query should get into the log file, then it must be saved
* to a separate file part in the format below
*
* File format
Hibernate: select je1_0.id from joke je1_0 where je1_0.hash_code=? fetch first ? rows only
2024-12-10T19:14:34.525 [scheduling-1] TRACE org.hibernate.orm.jdbc.bind - binding parameter (1:INTEGER) <- [718258863]
2024-12-10T19:14:34.525 [scheduling-1] TRACE org.hibernate.orm.jdbc.bind - binding parameter (2:INTEGER) <- [1]
 */

public class SqlGenFromLogger {
    static Pattern pattern = Pattern.compile("\\(\\d+:(.+)\\) <- \\[(.+)]");

    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{"d:/file.log"};
        }

        Arrays.stream(args).forEach(f -> System.out.println(sqlGen(f)));
    }

    public static String sqlGen(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
            String sql = lines.removeFirst().replace("Hibernate: ", "");
            for (String line : lines) {
                String param = getParam(line);
                sql = sql.replaceFirst("\\?", param);
            }
            return sql;
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private static String getParam(String line) {
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        if (matcher.group(1).toUpperCase().contains("INT")) {
            return matcher.group(2);
        } else {
            return "'" + matcher.group(2) + "'";
        }
    }
}