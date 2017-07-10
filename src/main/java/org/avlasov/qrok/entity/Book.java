package org.avlasov.qrok.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.avlasov.qrok.enums.Genre;
import org.avlasov.qrok.view.author.AuthorView;

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
    @JsonView(AuthorView.HideBooksAuthors.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (ISBN != null ? !ISBN.equals(book.ISBN) : book.ISBN != null) return false;
        if (genre != book.genre) return false;
        return authors != null ? authors.equals(book.authors) : book.authors == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (ISBN != null ? ISBN.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }
}
