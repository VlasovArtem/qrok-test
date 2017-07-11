package org.avlasov.qrok.controller;

import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = GET, path = "/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @RequestMapping(method = GET, path = "/info/{book_id}")
    public ResponseEntity<Book> getBook(@PathVariable("book_id") int id) {
        return bookService.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = GET, path = "/info/title/{book_title}")
    public ResponseEntity<Book> getBook(@PathVariable("book_title") String bookTitle) {
        return bookService.findByTitle(bookTitle)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = GET, path = "/info/isbn/{book_isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable("book_isbn") String isbn) {
        return bookService.findByISBN(isbn)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = POST, path = "/add")
    public ResponseEntity add(@NotNull @RequestBody Book book) {
        return bookService.add(book)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Book was not saved."));
    }

    @RequestMapping(method = PUT, path = "/update/{book_id}")
    public ResponseEntity update(@PathVariable("book_id") int id, @NotNull @RequestBody Book book) {
        return bookService.update(id, book)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("New book data is null or book id is not found in the database"));
    }

}
