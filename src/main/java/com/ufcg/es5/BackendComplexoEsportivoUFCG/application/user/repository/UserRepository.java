package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT userDetails
        FROM User userDetails
        WHERE userDetails.email = :email
    """
    )
    UserDetails findUserDetailsByEmail(
            @Param("email") String email
    );

    User findByEmail(String email);

    Optional<User> findByStudentId(String StudentId);
}
