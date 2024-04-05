package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserDataProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SaceUserRepository extends JpaRepository<SaceUser, Long> {

    @Query("""
                SELECT userDetails
                FROM SaceUser userDetails
                WHERE userDetails.email = :username
            """
    )
    UserDetails findUserDetailsByEmail(
            @Param("username") String username
    );

    SaceUser findByEmail(String email);

    Optional<SaceUser> findByStudentId(String StudentId);

    @Query("""
                SELECT user.id AS id
                FROM SaceUser user
                WHERE user.email = :email
            """
    )
    Optional<Long> findIdByEmail(
            @Param("email") String email);

    @Query("""
                SELECT user.name,
                       user.studentId,
                       user.email,
                       user.phoneNumber,
                       user.roleEnums
                FROM SaceUser user
            """)
    Collection<SaceUserDataProjection> findAllUsersAsDto();
}
