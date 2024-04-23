package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public record DeletedEvent<T>(T entity, Class<T> clazz) implements ResolvableTypeProvider {
    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), clazz);
    }
}
