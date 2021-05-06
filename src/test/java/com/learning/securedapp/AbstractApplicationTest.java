package com.learning.securedapp;

import com.learning.securedapp.configuration.AbstractMongoContainerBaseTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {
            "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration",
            "spring.mail.password=springboot",
            "spring.mail.username=spring",
            "spring.mail.host=127.0.0.1",
            "spring.mail.port=3025",
            "spring.mail.protocol=smtp",
            "spring.mail.test-connection=true"
        })
public abstract class AbstractApplicationTest extends AbstractMongoContainerBaseTest {}
