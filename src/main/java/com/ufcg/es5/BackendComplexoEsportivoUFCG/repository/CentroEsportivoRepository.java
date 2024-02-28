package com.ufcg.es5.BackendComplexoEsportivoUFCG.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.model.CentroEsportivo;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroEsportivoRepository extends JpaRepository<CentroEsportivo, Long> {
//    @Query(
//            "SELECT c.Reserva FROM CentroEsportivo c WHERE c = :complexoEsportivo AND c.Reserva.dataTimeInicial = :dataTimeInicial"
//    )
//    Reserva retornarReservaPorDataTimeInicialEComplexo(@Param("dataTimeInicial") String dataTimeInicial, @Param("complexoEsportivo") CentroEsportivo complexoEsportivo);
}
