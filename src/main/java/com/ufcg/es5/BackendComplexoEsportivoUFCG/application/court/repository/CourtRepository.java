package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    @Query("""
            SELECT court
            FROM Court court
            WHERE court.name = :name
                """)
    Court findByName(
            @Param("name")
            String name
    );

}
