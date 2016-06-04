package com.learning.securedapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	/*@LocalServerPort
    private int port;

    @Test
    public void testHome() throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate(HttpClientOption.SSL);
        ResponseEntity<String> entity = testRestTemplate
                .getForEntity("https://localhost:" + 8443, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(entity.getBody()).isEqualTo("Hello World");
    }*/
}
