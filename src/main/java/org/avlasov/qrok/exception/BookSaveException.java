package org.avlasov.qrok.exception;

/**
 * Created by artemvlasov on 12/07/2017.
 */
public class BookSaveException extends BookServiceException {

    public BookSaveException() {
    }

    public BookSaveException(String message) {
        super(message);
    }
}
