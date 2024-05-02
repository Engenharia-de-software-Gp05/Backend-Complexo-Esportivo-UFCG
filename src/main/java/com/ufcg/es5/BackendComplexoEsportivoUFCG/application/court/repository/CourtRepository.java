package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtBasicProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtDetailedProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtIdNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

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

    @Query("""
            SELECT court.id as id,
            court.name as name
            FROM Court court
                """)
    Collection<CourtBasicProjection> findAllCourtBasicProjection();

    @Query("""
            SELECT court.name as name,
            court.imagesUrls as imagesUrl,
            court.courtAvailabilityStatusEnum as courtAvailabilityStatusEnum,
            court.minimumIntervalBetweenReservation as minimumIntervalBetweenReservation
            FROM Court court
            WHERE court.id = :id
            """)
    CourtDetailedProjection findCourtDetailedProjectionById(
            @Param("id")
            Long id
    );

    @Query(
            """
                    SELECT court.id as id,
                           court.name as name
                        FROM Court court
                    """
    )
    Collection<CourtIdNameProjection> findIdName();
}
