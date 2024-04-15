package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtUpdateDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;

public interface CourtService extends BasicService<Court, Long> {
  
    CourtResponseDto create(CourtSaveDto data);

    CourtResponseDto updateById(CourtUpdateDto data, Long id);
  
    void deleteById(Long id);

    Court findByName(String name);

    Boolean existsByName(String name);
}
