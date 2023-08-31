package org.mjjaen.mongo;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@TestPropertySource(locations = "classpath:application_test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoApplicationTest
{
    @Test
    public void test() {
        assertTrue(true);
    }
}
