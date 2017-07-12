package org.avlasov.qrok.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.exception.BookSaveException;
import org.avlasov.qrok.repository.AuthorRepository;
import org.avlasov.qrok.repository.BookRepository;
import org.avlasov.qrok.service.BookService;
import org.avlasov.qrok.utils.BookUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);
    private final BookUpdater bookUpdater;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookUpdater bookUpdater, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookUpdater = bookUpdater;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(int id) {
        return Optional.ofNullable(bookRepository.findOne(id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        return bookRepository.findByISBN(isbn);
    }

    @Override
    public Optional<Book> add(Book book) {
        if (Objects.nonNull(book)) {
            if (Objects.isNull(book.getAuthors()) || book.getAuthors().isEmpty()) {
                LOGGER.warn("Book authors is not found");
                throw new BookSaveException("Book authors is not found");
            } else {
                book.setAuthors(book.getAuthors()
                        .stream()
                        .map(Author::getId)
                        .map(authorRepository::findOne)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
                return Optional.ofNullable(bookRepository.saveAndFlush(book));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(int id, Book source) {
        if (Objects.nonNull(source)) {
            Book target = bookRepository.findOne(id);
            if (Objects.nonNull(target)) {
                return Optional.of(bookUpdater.update(target, source));
            } else {
                LOGGER.warn("Book with tracking number {} is not found.", id);
            }
        } else {
            LOGGER.warn("Source book should not be null");
        }
        return Optional.empty();
    }
}
