package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.exceptions.NotFoundReservationException;
import com.microservices.warehouse.warehouses.mappers.CommentMapper;
import com.microservices.warehouse.warehouses.models.CommentEntity;
import com.microservices.warehouse.warehouses.models.ReservationEntity;
import com.microservices.warehouse.warehouses.models.ReviewEntity;
import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.requests.CommentRequest;
import com.microservices.warehouse.warehouses.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceManagement {

    private final CommentMapper commentMapper;

    private final ReviewService reviewService;
    private final WarehouseService warehouseService;

    public CommentResponse addComment(Long warehouseId, CommentRequest commentRequest) {

        // find the warehouse by id
        WarehouseEntity warehouse = warehouseService.findById(warehouseId);

        // find the warehouse's reviews by id
        ReviewEntity review = reviewService.findByWarehouseId(warehouseId);
        List<CommentEntity> comments = review.getComments();

        // convert comment request to comment entity
        comments.add(CommentEntity.builder()
                .commentBy(warehouse.getReserved()
                        .getReservations().stream()
                        .filter(s -> s.getReservationId().equals(commentRequest.reservationId()))
                        .map(ReservationEntity::getReservedBy)
                        .findAny().orElseThrow(() -> new NotFoundReservationException(commentRequest.reservationId().toString())))
                .score(commentRequest.score())
                .content(commentRequest.content())
                .commentAt(LocalDateTime.now())
                .build()
        );
        review.setComments(comments);

        // persist the comment
        return commentMapper.toResponse(reviewService.persist(review).getComments().getLast());
    }

    public Page<CommentResponse> findAllCommentsByWarehouseId(Long warehouseId, Pageable pageable) {

        // find the warehouse's reviews by id
        ReviewEntity review = reviewService.findByWarehouseId(warehouseId);

        // convert to page of responses
        return new PageImpl<>(
                review.getComments().stream().map(commentMapper::toResponse).toList(),
                pageable,
                review.getComments().size()
        );
    }
}
