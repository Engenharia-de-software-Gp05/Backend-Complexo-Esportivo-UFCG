package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
}
