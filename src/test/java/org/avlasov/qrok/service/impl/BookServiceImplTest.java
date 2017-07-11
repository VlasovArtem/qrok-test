package org.avlasov.qrok.service.impl;

import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.enums.Genre;
import org.avlasov.qrok.repository.BookRepository;
import org.avlasov.qrok.config.ServiceConfig;
import org.avlasov.qrok.utils.BookUpdater;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private BookUpdater bookUpdater;
    @Autowired
    private BookServiceImpl bookService;

    @Test
    public void findAll_WithData_ReturnCollection() throws Exception {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(getBookObject()));
        assertThat(bookService.findAll(), IsCollectionWithSize.hasSize(1));
    }

    @Test
    public void findById_WithExistingBook_ReturnBookOptional() throws Exception {
        when(bookRepository.findOne(Mockito.anyInt())).thenReturn(getBookObject());
        Optional<Book> book = bookService.findById(10);
        assertTrue(book.isPresent());
    }

    @Test
    public void findById_WithNotExistingBook_ReturnEmptyOptional() throws Exception {
        when(bookRepository.findOne(Mockito.anyInt())).thenReturn(null);
        Optional<Book> book = bookService.findById(10);
        assertFalse(book.isPresent());
    }

    @Test
    public void findByTitle_WithExistingBook_ReturnBookOptional() throws Exception {
        when(bookRepository.findByTitle(Mockito.anyString())).thenReturn(Optional.ofNullable(getBookObject()));
        Optional<Book> book = bookService.findByTitle("test title");
        assertTrue(book.isPresent());
    }

    @Test
    public void findByTitle_WithNotExistingBook_ReturnEmptyOptional() throws Exception {
        when(bookRepository.findByTitle(Mockito.anyString())).thenReturn(Optional.empty());
        Optional<Book> book = bookService.findByTitle("test title");
        assertFalse(book.isPresent());
    }

    @Test
    public void findByISBN_WithExistingBook_ReturnBookOptional() throws Exception {
        when(bookRepository.findByISBN(Mockito.anyString())).thenReturn(Optional.ofNullable(getBookObject()));
        Optional<Book> book = bookService.findByISBN("test isbn");
        assertTrue(book.isPresent());
    }

    @Test
    public void findByISBN_WithNotExistingBook_ReturnEmptyOptional() throws Exception {
        when(bookRepository.findByISBN(Mockito.anyString())).thenReturn(Optional.empty());
        Optional<Book> book = bookService.findByISBN("test isbn");
        assertFalse(book.isPresent());
    }

    @Test
    public void add_WithValidData_ReturnBookOptional() throws Exception {
        Book bookObject = getBookObject();
        bookObject.setId(10);
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(bookObject);
        Optional<Book> book = bookService.add(getBookObject());
        assertTrue(book.isPresent());
        assertThat(book.get().getId(), is(bookObject.getId()));
    }

    @Test
    public void add_WithNullBookArgument_ReturnEmptyOptional() throws Exception {
        Optional<Book> book = bookService.add(null);
        assertFalse(book.isPresent());
    }

    @Test
    public void deleteById_WithExistingId_ReturnTrue() throws Exception {
        doNothing().when(bookRepository).delete(Mockito.anyInt());
        when(bookRepository.exists(Mockito.anyInt())).thenReturn(true);
        assertTrue(bookService.deleteById(10));
    }

    @Test
    public void deleteById_WithNotExistingId_ReturnTrue() throws Exception {
        when(bookRepository.exists(Mockito.anyInt())).thenReturn(false);
        assertFalse(bookService.deleteById(10));
    }


    @Test
    public void deleteByTitle_WithExistingTitle_DoNothing() throws Exception {
        doNothing().when(bookRepository).deleteByTitle(Mockito.anyString());
        bookService.deleteByTitle("title");
    }

    @Test
    public void deleteByISBN_WithExistingISBN_DoNothing() throws Exception {
        doNothing().when(bookRepository).deleteByTitle(Mockito.anyString());
        bookService.deleteByISBN("isbn");
    }

    @Test
    public void update_WithValidData_ReturnBookOptional() throws Exception {
        when(bookRepository.findOne(Mockito.anyInt())).thenReturn(getBookObject());
        when(bookUpdater.update(Mockito.any(Book.class), Mockito.any(Book.class))).thenReturn(getBookObject());
        assertTrue(bookService.update(10, getBookObject()).isPresent());
    }

    @Test
    public void update_WithNullSource_ReturnEmptyOptional() throws Exception {
        assertFalse(bookService.update(10, null).isPresent());
    }

    @Test
    public void update_WithNotExistingTarget_ReturnEmptyOptional() throws Exception {
        when(bookRepository.findOne(Mockito.anyInt())).thenReturn(null);
        assertFalse(bookService.update(10, getBookObject()).isPresent());
    }

    public Book getBookObject() {
        return getBookObject("Title", "56565", Genre.ACTION, null);
    }

    private Book getBookObject(String title, String ISBN, Genre genre, List<Author> authors) {
        return new Book(title, ISBN, genre, authors);
    }
}