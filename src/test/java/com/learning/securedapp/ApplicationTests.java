package com.learning.securedapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class ApplicationTests
{

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
            "build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private RestDocumentationResultHandler document;

    @Before
    public void setUp()
    {
        this.document = document("{method-name}", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document).build();
    }

    @Test
    public void contextLoads()
    {
    }

    @LocalServerPort
    private int port;

    @Test
    public void testHome() throws Exception
    {
        TestRestTemplate testRestTemplate = new TestRestTemplate(HttpClientOption.SSL);
        ResponseEntity<String> entity = testRestTemplate
                .getForEntity("https://localhost:" + 8443 + "/login.html", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK); //
        assertThat(entity.getBody()).isEqualTo("Hello World");
    }

}
