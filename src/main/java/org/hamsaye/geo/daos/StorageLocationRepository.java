package org.hamsaye.geo.daos;

import org.hamsaye.geo.models.StorageLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocationEntity, Long> {

}
