package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicTestController {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    protected AuthenticatedUser authenticatedUser;


    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setContext() {
        objectMapper = new ObjectMapper();
        authenticatedUser = new AuthenticatedUser();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
}
