package org.avlasov.qrok.utils;

import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.entity.Reward;
import org.avlasov.qrok.enums.Sex;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 11/07/2017.
 */
public class AuthorUpdaterTest {

    private AuthorUpdater authorUpdater;

    public AuthorUpdaterTest() {
        this.authorUpdater = new AuthorUpdater();
    }

    @Test
    public void update_WithValidSourceAndTarget_ReturnUpdatedAuthor() throws Exception {
        Author source = getAuthor(0, "First name", "New Last Name", Sex.MALE, Collections.singletonList(new Book()), LocalDate.now(), null);
        Author target = getAuthor(11, "First name", "Old Last Name", Sex.MALE, null, LocalDate.now(), Collections.singletonList(new Reward()));
        Author updatedTarget = authorUpdater.update(target, source);
        assertEquals(updatedTarget.getLastName(), source.getLastName());
        assertThat(updatedTarget.getBooks(), IsCollectionWithSize.hasSize(1));
        assertThat(updatedTarget.getId(), not(0));
        assertThat(updatedTarget.getRewards(), IsCollectionWithSize.hasSize(1));
    }

    @Test
    public void update_WithNullTarget_ReturnNull() throws Exception {
        assertNull(authorUpdater.update(null, new Author()));
    }

    @Test
    public void update_WithNullSource_ReturnTargetBook() throws Exception {
        assertNotNull(authorUpdater.update(new Author(), null));
    }

    @Test
    public void update_WithEqualsTargetAndSource_ReturnTargetBook() throws Exception {
        assertNotNull(authorUpdater.update(getAuthor(0, "First name", "New Last Name", Sex.MALE, Collections.singletonList(new Book()), LocalDate.now(), null),
                getAuthor(0, "First name", "New Last Name", Sex.MALE, Collections.singletonList(new Book()), LocalDate.now(), null)));
    }

    private Author getAuthor(int id, String firstname, String lastname, Sex sex, List<Book> books, LocalDate birthDate, List<Reward> rewards) {
        Author author = new Author(firstname, lastname, sex, books, birthDate, rewards);
        author.setId(id);
        return author;
    }

}