package org.avlasov.qrok.repository;

import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.enums.Genre;
import org.avlasov.qrok.repository.config.DatabaseConfig;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = DatabaseConfig.class)
@SqlGroup(value = {
        @Sql("/scripts/book/drop-data.sql"),
        @Sql("/scripts/book/data.sql")
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findOne_WithExistingId_ReturnBook() throws Exception {
        Book book = bookRepository.getOne(5);
        assertTrue(Objects.nonNull(book));
        assertEquals(book.getTitle(), "The Great Gatsby");
    }

    @Test
    public void findOne_WithNotExistingId_ReturnEmptyBook() throws Exception {
        assertThat(bookRepository.findOne(10), nullValue());
    }

    @Test
    public void findByTitle_WithExistingBookTitle_ReturnBookOptional() throws Exception {
        Optional<Book> book = bookRepository.findByTitle("Murder on the Orient Express");
        assertTrue(book.isPresent());
        assertEquals(book.get().getTitle(), "Murder on the Orient Express");
    }

    @Test
    public void findByTitle_WithNotExistingBookTitle_ReturnEmptyOptional() throws Exception {
        Optional<Book> book = bookRepository.findByTitle("missing title");
        assertFalse(book.isPresent());
    }

    @Test
    public void findByTitle_WithNullTitle_ReturnEmptyOptional() throws Exception {
        Optional<Book> book = bookRepository.findByTitle(null);
        assertFalse(book.isPresent());
    }

    @Test
    public void findByISBN_WithExistingISBN_ReturnBookOptional() throws Exception {
        Optional<Book> book = bookRepository.findByISBN("978544671809");
        assertTrue(book.isPresent());
        assertEquals(book.get().getISBN(), "978544671809");
    }

    @Test
    public void findByISBN_WithNotExistingISBN_ReturnEmptyOptional() throws Exception {
        Optional<Book> book = bookRepository.findByISBN("missing isbn");
        assertFalse(book.isPresent());
    }

    @Test
    public void findByISBN_WithNullISBN_ReturnEmptyOptional() throws Exception {
        Optional<Book> book = bookRepository.findByISBN(null);
        assertFalse(book.isPresent());
    }

    @Test
    public void delete_WithExistingId_RemoveData() throws Exception {
        int booksSize = bookRepository.findAll().size();
        bookRepository.delete(1);
        assertThat(bookRepository.findAll(), IsCollectionWithSize.hasSize(booksSize - 1));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete_WithNotExistingId_RemoveData() throws Exception {
        int booksSize = bookRepository.findAll().size();
        bookRepository.delete(999);
        assertThat(bookRepository.findAll(), IsCollectionWithSize.hasSize(booksSize));
    }

    @Test
    public void deleteByTitle_WithExistingBookTitle_RemoveData() throws Exception {
        int booksSize = bookRepository.findAll().size();
        bookRepository.deleteByTitle("Before the Law");
        assertThat(bookRepository.findAll(), IsCollectionWithSize.hasSize(booksSize - 1));
    }

    @Test
    public void deleteByTitle_WithNotExistingBookTitle_RemoveData() throws Exception {
        int booksSize = bookRepository.findAll().size();
        bookRepository.findByTitle("not exits");
        assertThat(bookRepository.findAll(), IsCollectionWithSize.hasSize(booksSize));
    }

    @Test
    public void deleteByISBN_WithExistingISBN_RemoveData() throws Exception {
        int booksSize = bookRepository.findAll().size();
        bookRepository.deleteByISBN("9788360979426");
        assertThat(bookRepository.findAll(), IsCollectionWithSize.hasSize(booksSize - 1));
    }

    @Test
    public void deleteByISBN_WithNotExistingISBN_RemoveData() throws Exception {
        int booksSize = bookRepository.findAll().size();
        bookRepository.deleteByTitle("not exists");
        assertThat(bookRepository.findAll(), IsCollectionWithSize.hasSize(booksSize));
    }

    @Test
    public void add_WithValidBook_ReturnSavedBook() throws Exception {
        Book book = new Book("New Title", "562929", Genre.ADVENTURE, null);
        Book save = bookRepository.save(book);
        bookRepository.flush();
        assertThat(save.getId(), not(0));
    }

}