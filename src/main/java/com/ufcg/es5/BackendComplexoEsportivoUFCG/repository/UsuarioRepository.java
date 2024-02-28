package com.ufcg.es5.BackendComplexoEsportivoUFCG.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.model.CentroEsportivo;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
