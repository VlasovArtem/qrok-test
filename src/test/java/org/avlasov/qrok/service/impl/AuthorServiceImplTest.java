package org.avlasov.qrok.service.impl;

import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Reward;
import org.avlasov.qrok.repository.AuthorRepository;
import org.avlasov.qrok.service.AuthorService;
import org.avlasov.qrok.utils.AuthorUpdater;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private AuthorUpdater authorUpdater;
    @Autowired
    private AuthorService authorService;

    @Test
    public void findAll_WithValidData_ReturnCollection() throws Exception {
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(new Author()));
        assertThat(authorService.findAll(), IsCollectionWithSize.hasSize(1));
    }

    @Test
    public void findById_WithExistingId_ReturnAuthorOptional() throws Exception {
        when(authorRepository.findOne(anyInt())).thenReturn(new Author());
        assertTrue(authorService.findById(10).isPresent());
    }

    @Test
    public void findById_WithNotExistingId_ReturnEmptyOptional() throws Exception {
        when(authorRepository.findOne(anyInt())).thenReturn(null);
        assertFalse(authorService.findById(10).isPresent());
    }

    @Test
    public void findByFirstName_WithExistingFirstName_ReturnAuthorOptional() throws Exception {
        when(authorRepository.findByFirstName(anyString())).thenReturn(Optional.of(new Author()));
        assertTrue(authorService.findByFirstName("test").isPresent());
    }

    @Test
    public void findByFirstName_WithNotExistingFirstName_ReturnEmptyOptional() throws Exception {
        when(authorRepository.findByFirstName(anyString())).thenReturn(Optional.empty());
        assertFalse(authorService.findByFirstName("test").isPresent());
    }

    @Test
    public void findByLastName_WithExistingLastName_ReturnAuthorOptional() throws Exception {
        when(authorRepository.findByLastName(anyString())).thenReturn(Optional.of(new Author()));
        assertTrue(authorService.findByLastName("test").isPresent());
    }

    @Test
    public void findByLastName_WithNotExistingLastName_ReturnEmptyOptional() throws Exception {
        when(authorRepository.findByLastName(anyString())).thenReturn(Optional.empty());
        assertFalse(authorService.findByLastName("test").isPresent());
    }

    @Test
    public void add_WithValidData_ReturnAuthorOptional() throws Exception {
        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(new Author());
        assertTrue(authorService.add(new Author()).isPresent());
    }

    @Test
    public void update_WithValidData_ReturnAuthorOptional() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(true);
        when(authorRepository.findOne(Mockito.anyInt())).thenReturn(new Author());
        when(authorUpdater.update(Mockito.any(Author.class), Mockito.any(Author.class))).thenReturn(new Author());
        assertTrue(authorService.update(10, new Author()).isPresent());
    }

    @Test
    public void update_WithNotExistingTarget_ReturnEmptyOptional() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(false);
        assertFalse(authorService.update(10, new Author()).isPresent());
    }

    @Test
    public void update_WithNullSource_ReturnEmptyOptional() throws Exception {
        assertFalse(authorService.update(10, null).isPresent());
    }

    @Test
    public void addAuthorReward_WithEmptyRewards_ReturnAuthorOptional() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(true);
        when(authorRepository.findOne(anyInt())).thenReturn(new Author());
        Optional<Author> author = authorService.addAuthorReward(10, new Reward());
        assertTrue(author.isPresent());
        assertThat(author.get().getRewards(), IsCollectionWithSize.hasSize(1));
    }

    @Test
    public void addAuthorReward_WithExistingRewards_ReturnAuthorOptional() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(true);
        Author author = new Author();
        author.setRewards(new ArrayList<Reward>(){{
                    add(new Reward());
                }});
        when(authorRepository.findOne(anyInt())).thenReturn(author);
        Optional<Author> authorOptional = authorService.addAuthorReward(10, new Reward());
        assertTrue(authorOptional.isPresent());
        assertThat(authorOptional.get().getRewards(), IsCollectionWithSize.hasSize(2));
    }

    @Test
    public void addAuthorReward_WithNullReward_ReturnEmptyOptional() throws Exception {
        Optional<Author> authorOptional = authorService.addAuthorReward(10, null);
        assertFalse(authorOptional.isPresent());
    }

    @Test
    public void addAuthorReward_WithNotExistingId_ReturnEmptyOptional() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(false);
        Optional<Author> authorOptional = authorService.addAuthorReward(10, new Reward());
        assertFalse(authorOptional.isPresent());
    }

    @Test
    public void delete_WithExistingId_ReturnTrue() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(true);
        doNothing().when(authorRepository).delete(anyInt());
        assertTrue(authorService.delete(10));
    }

    @Test
    public void delete_WithNotExistingId_ReturnFalse() throws Exception {
        when(authorRepository.exists(anyInt())).thenReturn(false);
        assertFalse(authorService.delete(10));
    }

}