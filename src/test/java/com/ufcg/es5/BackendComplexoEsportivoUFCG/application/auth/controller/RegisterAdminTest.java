package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthAttributesConstants.*;
import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterAdminTest extends BasicTestController {

    private static AuthRegisterDataWithRolesDto validPayload;
    private static Set<String> validRoles;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void makeScenario() {
        validPayload = new AuthRegisterDataWithRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, Set.of(SaceUserRoleEnum.ROLE_ADMIN));
        validRoles = Set.of(ROLE_ADMIN);
    }

    @ParameterizedTest
    @DisplayName("Should return Created. Code: 201")
    @MethodSource(value = "returnCreated")
    void returnCreated(Set<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        String payload = makeRequestPayload(validPayload);
        SaceUserNameEmailDto response = makeResponse();

        Mockito.when(authService.registerByAdmin(validPayload))
                .thenReturn(response);

        callEndpoint(payload).andExpect(status().isCreated()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(AuthRegisterDataWithRolesDto registerDataWithRolesDto) throws Exception {
        SecurityContextUtils.fakeAuthentication(validRoles);
        String payload = makeRequestPayload(registerDataWithRolesDto);

        callEndpoint(payload).andExpect(status().isBadRequest()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnForbidden")
    void returnForbidden(Set<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);

        String payload = makeRequestPayload(validPayload);

        callEndpoint(payload).andExpect(status().isForbidden()).andReturn();
    }

    private static Stream<Arguments> returnCreated() {
        return Stream.of(
                Arguments.of(Set.of(ROLE_ADMIN)),
                Arguments.of(Set.of(ROLE_ADMIN, ROLE_USER))
        );
    }

    private static Stream<Arguments> returnBadRequest() {
        Set<SaceUserRoleEnum> emptySet = new HashSet<>();
        return Stream.of(
                Arguments.of(new AuthRegisterDataWithRolesDto("", VALID_NAME, VALID_PHONE_NUMBER, Set.of(SaceUserRoleEnum.ROLE_ADMIN))),
                Arguments.of(new AuthRegisterDataWithRolesDto(null, VALID_NAME, VALID_PHONE_NUMBER, Set.of(SaceUserRoleEnum.ROLE_ADMIN))),
                Arguments.of(new AuthRegisterDataWithRolesDto(VALID_EMAIL, "", VALID_PHONE_NUMBER, Set.of(SaceUserRoleEnum.ROLE_ADMIN))),
                Arguments.of(new AuthRegisterDataWithRolesDto(VALID_EMAIL, null, VALID_PHONE_NUMBER, Set.of(SaceUserRoleEnum.ROLE_ADMIN))),
                Arguments.of(new AuthRegisterDataWithRolesDto(VALID_EMAIL, VALID_NAME, "", Set.of(SaceUserRoleEnum.ROLE_ADMIN))),
                Arguments.of(new AuthRegisterDataWithRolesDto(VALID_EMAIL, VALID_NAME, null, Set.of(SaceUserRoleEnum.ROLE_ADMIN))),
                Arguments.of(new AuthRegisterDataWithRolesDto(VALID_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, null)),
                Arguments.of(new AuthRegisterDataWithRolesDto(VALID_EMAIL, VALID_NAME, null, emptySet))
        );
    }

    private static Stream<Arguments> returnForbidden() {
        return Stream.of(
                Arguments.of(Set.of(ROLE_USER)),
                Arguments.of(Set.of(ROLE_PENDING))
        );
    }

    private String makeRequestPayload(AuthRegisterDataWithRolesDto payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }

    private SaceUserNameEmailDto makeResponse() {
        return new SaceUserNameEmailDto(VALID_NAME, VALID_STUDENT_EMAIL);
    }

    private ResultActions callEndpoint(String payload) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(AuthPathConstants.REGISTER_ADMIN_FULL_PATH)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }

}
