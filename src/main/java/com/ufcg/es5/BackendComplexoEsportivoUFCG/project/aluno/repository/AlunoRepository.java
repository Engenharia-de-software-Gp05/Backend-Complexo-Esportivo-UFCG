package com.ufcg.es5.BackendComplexoEsportivoUFCG.project.aluno.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
