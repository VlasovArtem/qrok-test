package org.avlasov.qrok.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.avlasov.qrok.enums.Sex;
import org.avlasov.qrok.view.book.BookView;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@Entity
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHOR_ID", unique = true, nullable = false)
    private int id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "AUTHOR_BOOK",
            joinColumns = @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID", nullable = false, updatable = false))
    @JsonView({BookView.HideAuthorsBooks.class})
    private List<Book> books;
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "REWARD_ID", referencedColumnName = "AUTHOR_ID")
    private List<Reward> rewards;

    public Author() {}

    public Author(String firstName, String lastName, Sex sex, List<Book> books, LocalDate birthDate, List<Reward> rewards) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.books = books;
        this.birthDate = birthDate;
        this.rewards = rewards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }
}
