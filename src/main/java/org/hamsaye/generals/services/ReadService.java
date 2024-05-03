package org.hamsaye.generals.services;

import java.util.List;

public interface ReadService<T> {
    List<T> findAll();
}
