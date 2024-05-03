package org.hamsaye.generals.services;

import java.util.Map;
import java.util.Optional;

public interface ConditionalReadService<T> extends ReadService<T> {
    Optional<T> findAll(Map<String, Object> status);
}
