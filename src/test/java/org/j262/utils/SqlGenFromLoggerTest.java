package org.j262.utils;


import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlGenFromLoggerTest {

    private final String logfileData = """
            Hibernate: select je1_0.id from joke je1_0 where je1_0.hash_code=? fetch first ? rows only
            2024-12-10T19:14:34.525 [scheduling-1] TRACE org.hibernate.orm.jdbc.bind - binding parameter (1:INTEGER) <- [718258863]
            2024-12-10T19:14:34.525 [scheduling-1] TRACE org.hibernate.orm.jdbc.bind - binding parameter (2:INTEGER) <- [1]
            """;

    private final String expectedSql = "select je1_0.id from joke je1_0 where je1_0.hash_code=718258863 fetch first 1 rows only";

    @Test
    public void testSqlGen() throws Exception {
        File tempFile = File.createTempFile("log-", ".log");
        Files.writeString(tempFile.toPath(), logfileData);
        String detectedSql = SqlGenFromLogger.sqlGen(tempFile.getAbsolutePath());
        assertTrue(tempFile.delete());
        assertEquals(expectedSql, detectedSql);

    }
}