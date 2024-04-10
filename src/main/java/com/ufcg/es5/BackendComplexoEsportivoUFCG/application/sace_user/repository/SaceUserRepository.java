package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserDataProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserNameEmailProjection;
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

    @Query("""
                SELECT user
                FROM SaceUser user
                WHERE user.email = :email
            """
    )
    Optional<SaceUser> findByEmail(
            @Param("email") String email
    );

    @Query("""
                SELECT user
                FROM SaceUser user
                WHERE user.studentId = :studentId
            """
    )
    Optional<SaceUser> findByStudentId(
            @Param("studentId") String studentId
    );

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

    @Query(
            """
                    SELECT user.name as name,
                           user.email as email
                    FROM SaceUser user
                    WHERE user.id = :id
                    """
    )
    SaceUserNameEmailProjection findNameEmailById(
            @Param("id") Long id
    );

    @Query(
            """
                    SELECT user
                    FROM SaceUser user
                    WHERE user.email = :email AND user.password = :password
                    """
    )
    Optional<SaceUser> findByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password);
}
