package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

class LoginTest extends BasicTestService {

    @Autowired
    private SaceUserService userService;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void makeTestScenario() {
        SaceUser user = new SaceUser(

        );
    }
}
