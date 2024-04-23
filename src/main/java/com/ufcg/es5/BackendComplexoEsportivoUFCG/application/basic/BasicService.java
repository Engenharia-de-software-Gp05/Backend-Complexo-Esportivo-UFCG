package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BasicService <T extends BasicEntity, ID>{
    public default List<T> findAll() {
        return getRepository().findAll();
    }

    public default Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    public default List<T> findByIds(List<ID> ids){
        return getRepository().findAllById(ids);
    }

    public default boolean exists(ID id){
        return getRepository().existsById(id);
    }

    @Transactional
    public default T save(T entity){
        return getRepository().save(entity);
    }

    @Transactional
    public default List<T> save(Collection<T> entities){
        return getRepository().saveAll(entities);
    }

    @Transactional
    public default void delete(T entity){getRepository().delete(entity);}

    @Transactional
    public default void deleteById(ID id){
        getRepository().deleteById(id);
    }

    @Transactional
    public default void deleteAll(){
        getRepository().deleteAll();
    }

    public JpaRepository<T, ID> getRepository();
}
