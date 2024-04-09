package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SignUpConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface SignUpConfirmationCodeRepository extends JpaRepository<SignUpConfirmationCode, Long> {

    @Query(
            """
                            SELECT confirmationCode
                            FROM SignUpConfirmationCode confirmationCode
                            WHERE confirmationCode.user.id = :userId
                    """
    )
    Optional<SignUpConfirmationCode> findByUserId(
            @Param("userId") Long userId
    );

    @Query(
            """
                            SELECT cc
                            FROM SignUpConfirmationCode cc
                            WHERE cc.user.id = :userId
                            AND cc.confirmationCode = :confirmationCode
                    """
    )
    Optional<SignUpConfirmationCode> findByUserIdAndConfirmationCode(
            @Param("userId") Long userId,
            @Param("confirmationCode") String confirmationCode);


    @Query(
            """
                            SELECT cc.id
                            FROM SignUpConfirmationCode cc
                            WHERE cc.createdAt <= :dateTime
                    """
    )
    Collection<Long> findAllBeforeDateTime(
            @Param("dateTime") LocalDateTime dateTime
    );
}
