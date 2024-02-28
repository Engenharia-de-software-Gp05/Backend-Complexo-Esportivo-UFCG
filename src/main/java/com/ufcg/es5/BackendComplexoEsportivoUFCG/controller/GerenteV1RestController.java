package com.ufcg.es5.BackendComplexoEsportivoUFCG.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/gerente",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class GerenteV1RestController {
}
