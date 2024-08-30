package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.requests.CommentRequest;
import com.microservices.warehouse.storages.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentMapper {

    private final ReservationService reservationService;

    public CommentEntity toComment(CommentRequest request) {
        return CommentEntity.builder()
                .commentBy(reservationService.findReservationById(
                        request.reservationId()
                ).getReservedBy())
                .content(request.content())
                .score(request.score())
                .build();
    }

    public CommentResponse toResponse(CommentEntity comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .commentBy(comment.getCommentBy())
                .score(comment.getScore())
                .content(comment.getContent())
                .commentAt(comment.getCommentAt())
                .displayable(comment.isEnabled())
                .build();
    }
}
