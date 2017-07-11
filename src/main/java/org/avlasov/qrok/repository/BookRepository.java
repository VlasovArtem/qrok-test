package org.avlasov.qrok.repository;

import org.avlasov.qrok.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by artemvlasov on 10/07/2017.
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);
    @Query("SELECT b FROM Book as b WHERE b.ISBN = ?1")
    Optional<Book> findByISBN(String isbn);
    void deleteByTitle(String title);
    @Transactional
    @Modifying
    @Query("DELETE FROM Book WHERE ISBN = ?1")
    void deleteByISBN(String isbn);

}
