package org.avlasov.qrok.repository;

import org.avlasov.qrok.config.DatabaseConfig;
import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.enums.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = DatabaseConfig.class)
@SqlGroup(value = {
        @Sql("/scripts/drop-data.sql"),
        @Sql("/scripts/data.sql")
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

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
    public void add_WithValidBook_ReturnSavedBook() throws Exception {
        Book book = new Book("New Title", "562929", Genre.ADVENTURE, Collections.singletonList(authorRepository.findOne(1)));
        Book save = bookRepository.save(book);
        bookRepository.flush();
        assertThat(save.getId(), not(0));
        Optional<Author> first = bookRepository.findOne(save.getId()).getAuthors().stream().findFirst();
        assertTrue(first.isPresent());
        assertThat(first.get().getId(), is(1));
    }

}