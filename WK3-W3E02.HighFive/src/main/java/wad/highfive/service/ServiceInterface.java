package wad.highfive.service;

import java.util.Collection;

public interface ServiceInterface<T> {
    T create(T object);
    T findById(Long id);
    void delete(T object);
    void delete(Long id);
    Collection<T> findAll();
}
