package org.avlasov.qrok.entity;

import org.avlasov.qrok.enums.Genre;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ISBN"),
        @UniqueConstraint(columnNames = "TITLE")
})
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", unique = true, nullable = false)
    private int id;
    private String title;
    @Column(length = 13)
    private String ISBN;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    private List<Author> authors;

    public Book() {
    }

    public Book(String title, String ISBN, Genre genre, List<Author> authors) {
        this.title = title;
        this.ISBN = ISBN;
        this.genre = genre;
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
