package org.avlasov.qrok.repository;

import org.avlasov.qrok.config.DatabaseConfig;
import org.avlasov.qrok.entity.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = DatabaseConfig.class)
@SqlGroup(value = {
        @Sql("/scripts/author/drop-data.sql"),
        @Sql("/scripts/author/data.sql")
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findOne_WithExistingId_ReturnAuthor() throws Exception {
        Author author = authorRepository.findOne(1);
        assertEquals(author.getLastName(), "Palahniuk");
    }

    @Test
    public void findOne_WithNotExistingId_ReturnNull() throws Exception {
        Author author = authorRepository.findOne(11);
        assertNull(author);
    }

    @Test
    public void findByFirstName_WithExistingFirstName_ReturnAuthorOptional() throws Exception {
        Optional<Author> author = authorRepository.findByFirstName("F. Scott");
        assertTrue(author.isPresent());
        assertEquals(author.get().getFirstName(), "F. Scott");
    }

    @Test
    public void findByFirstName_WithNotExistingFirstName_ReturnEmptyOptional() throws Exception {
        Optional<Author> author = authorRepository.findByFirstName("Missing Firstname");
        assertFalse(author.isPresent());
    }

    @Test
    public void findByLastName_WithExistingLastName_ReturnAuthorOptional() throws Exception {
        Optional<Author> author = authorRepository.findByLastName("Kafka");
        assertTrue(author.isPresent());
        assertEquals(author.get().getLastName(), "Kafka");
    }

    @Test
    public void findByLastName_WithNotExistingLastName_ReturnEmptyOptional() throws Exception {
        Optional<Author> author = authorRepository.findByLastName("Missing Lastname");
        assertFalse(author.isPresent());
    }
}