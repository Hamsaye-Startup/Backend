package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.requests.CommentRequest;
import com.microservices.warehouse.storages.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class is a mapper class for converting between Comment entity, request, and response objects.
 * It helps in converting data from the CommentEntity (database entity) to CommentRequest (incoming request)
 * and CommentResponse (outgoing response) objects.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentMapper {

    /**
     * See {@link com.microservices.warehouse.reservations.services.ReservationService}
     * for more details, please.
     */
    private final ReservationService reservationService;

    /**
     * This method is used to convert a CommentRequest object to a CommentEntity object.
     * It extracts necessary information like the comment content, score, and the user who made the reservation.
     *
     * @param request The CommentRequest object containing the details of the comment to be created.
     * @return A CommentEntity object representing the comment in the database.
     * @since 1.0
     */
    public CommentEntity toComment(CommentRequest request) {
        return CommentEntity.builder()
                .commentBy(reservationService.findReservationById(
                        request.reservationId()
                ).getReservedBy())
                .content(request.content())
                .score(request.score())
                .build();
    }

    /**
     * This method converts a CommentEntity object into a CommentResponse object.
     * It provides details about the comment, such as the comment's ID, content, author, and whether it is enabled/displayable.
     *
     * @param comment The CommentEntity object representing the comment from the database.
     * @return A CommentResponse object containing the information of the comment to be sent to the client.
     * @since 1.0
     */
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
