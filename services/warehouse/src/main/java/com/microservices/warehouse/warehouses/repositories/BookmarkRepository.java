package com.microservices.warehouse.warehouses.repositories;

import com.microservices.warehouse.warehouses.models.BookmarkEntity;
import com.microservices.warehouse.warehouses.models.keys.BookmarkId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkId> {

    @Query("select b from BookmarkEntity b where b.id.uid = :uid order by b.createdAt desc")
    Page<BookmarkEntity> findById(@Param("uid") UUID uid, Pageable pageable);

    @Query("select b from BookmarkEntity b where b.id.uid = :uid and b.id.warehouse.id = :warehouseId")
    Optional<BookmarkEntity> findByIdAndWarehouseId(@Param("uid") UUID uid, @Param("warehouseId") Long warehouseId);

    @Query(value = "select exists(select 1 from tb_storage_bookmark b where b.uid = :uid and b.warehouse_id = :warehouseId)", nativeQuery = true)
    boolean existByIdAndWarehouseId(@Param("uid") UUID uid, @Param("warehouseId") Long warehouseId);
}
