package org.avlasov.qrok.utils;

import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.enums.Genre;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 11/07/2017.
 */
public class BookUpdaterTest {

    private BookUpdater bookUpdater;

    public BookUpdaterTest() {
        this.bookUpdater = new BookUpdater();
    }

    @Test
    public void update_WithValidSourceAndTarget_ReturnUpdatedBook() throws Exception {
        Book source = getBookObject(0, "New title", "85959595", Genre.ADVENTURE, Collections.singletonList(new Author()));
        Book target = getBookObject(11, "Old Title", "85959595", Genre.ACTION, null);
        Book updatedTarget = bookUpdater.update(target, source);
        assertEquals(updatedTarget.getTitle(), "New title");
        assertThat(updatedTarget.getAuthors(), IsCollectionWithSize.hasSize(1));
        assertThat(updatedTarget.getId(), not(0));
    }

    @Test
    public void update_WithNullTarget_ReturnNull() throws Exception {
        assertNull(bookUpdater.update(null, getBookObject(11, "Old Title", "85959595", Genre.ACTION, null)));
    }

    @Test
    public void update_WithNullSource_ReturnTargetBook() throws Exception {
        assertNotNull(bookUpdater.update(getBookObject(11, "Old Title", "85959595", Genre.ACTION, null), null));
    }

    @Test
    public void update_WithEqualsTargetAndSource_ReturnTargetBook() throws Exception {
        assertNotNull(bookUpdater.update(getBookObject(11, "Old Title", "85959595", Genre.ACTION, null), getBookObject(11, "Old Title", "85959595", Genre.ACTION, null)));
    }

    private Book getBookObject(int id, String title, String ISBN, Genre genre, List<Author> authors) {
        Book book = new Book(title, ISBN, genre, authors);
        book.setId(id);
        return book;
    }

}