package org.hamsaye.generals.services;

public interface WriteService<T> {

    T persist(T object);

    T persistAndFlush(T object);

    T update(T object);

    void delete(T object);
}
