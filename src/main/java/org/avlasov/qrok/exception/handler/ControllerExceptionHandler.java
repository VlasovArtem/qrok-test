package org.avlasov.qrok.exception.handler;

import org.avlasov.qrok.exception.BookServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by artemvlasov on 12/07/2017.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BookServiceException.class)
    private ResponseEntity bookServiceExceptionHandler(BookServiceException ex) {
        return createResponseEntity(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    private ResponseEntity createResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(message);
    }

}
