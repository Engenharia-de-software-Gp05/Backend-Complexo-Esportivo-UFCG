package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
