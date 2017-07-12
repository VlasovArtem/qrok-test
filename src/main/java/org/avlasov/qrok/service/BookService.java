package org.avlasov.qrok.service;

import org.avlasov.qrok.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Created by artemvlasov on 10/07/2017.
 */
public interface BookService {

    List<Book> findAll();
    Optional<Book> findById(int id);
    Optional<Book> findByTitle(String title);
    Optional<Book> findByISBN(String isbn);
    Optional<Book> add(Book book);
    Optional<Book> update(int id, Book source);

}
