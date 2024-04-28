package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.s3.S3Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicTestService {
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    protected S3Uploader s3Uploader;

    @BeforeEach
    public void setContext() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @TestConfiguration
    static class ApplicationEventPublisherConfiguration {
        @Bean
        @Primary
        ApplicationEventPublisher publisher() {
            return Mockito.mock(ApplicationEventPublisher.class);
        }
    }
}
