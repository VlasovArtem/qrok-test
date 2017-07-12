package org.avlasov.qrok.exception;

/**
 * Created by artemvlasov on 12/07/2017.
 */
public class BookServiceException extends RuntimeException {

    public BookServiceException() {
    }

    public BookServiceException(String message) {
        super(message);
    }
}
