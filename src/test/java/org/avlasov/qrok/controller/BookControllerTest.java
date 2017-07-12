package org.avlasov.qrok.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.avlasov.qrok.entity.Book;
import org.avlasov.qrok.enums.Genre;
import org.avlasov.qrok.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = BookController.class)
@WithMockUser(username = "qrok", password = "qrok-password")
public class BookControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    public BookControllerTest() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void getAllBooks_ReturnCollection() throws Exception {
        List<Book> books = Collections.singletonList(getBookObject());
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @Test
    public void getBook_WithExistingBookId_ReturnResponseEntityWithBook() throws Exception {
        Book bookObject = getBookObject();
        when(bookService.findById(anyInt())).thenReturn(Optional.of(bookObject));
        mockMvc.perform(get("/book/info/10"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookObject)));
    }

    @Test
    public void getBook_WithNotExistingBookId_ReturnResponseEntityWithNotFoundStatus() throws Exception {
        when(bookService.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/book/info/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBook_WithExistingBookTitle_ReturnResponseEntityWithBook() throws Exception {
        Book bookObject = getBookObject();
        when(bookService.findByTitle(anyString())).thenReturn(Optional.of(bookObject));
        mockMvc.perform(get("/book/info/title/test title"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookObject)));
    }

    @Test
    public void getBook_WithNotExistingBookTitle_ReturnResponseEntityWithNotFoundStatus() throws Exception {
        when(bookService.findByTitle(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/book/info/title/test title"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBookByISBN_WithExistingBookISBN_ReturnResponseEntityWithBook() throws Exception {
        Book bookObject = getBookObject();
        when(bookService.findByISBN(anyString())).thenReturn(Optional.of(bookObject));
        mockMvc.perform(get("/book/info/isbn/65656565"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookObject)));
    }

    @Test
    public void getBookByISBN_WithExistingBookISBN_ReturnResponseEntityWithNotFoundStatus() throws Exception {
        when(bookService.findByISBN(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/book/info/isbn/65656565"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void add_WithValidBook_ReturnResponseEntityWithBook() throws Exception {
        when(bookService.add(getBookObject())).thenReturn(Optional.of(getBookObject()));
        mockMvc.perform(post("/book/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getBookObject())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getBookObject())));
    }

    @Test
    public void add_WithAddReturnEmpty_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        mockMvc.perform(post("/book/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void add_WithInvalidBook_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(bookService.add(getBookObject())).thenReturn(Optional.empty());
        mockMvc.perform(post("/book/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getBookObject())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book was not saved."));
    }

    @Test
    public void update_WithValidaIdAndBook_ReturnResponseEntityWithBook() throws Exception {
        when(bookService.update(anyInt(), any(Book.class))).thenReturn(Optional.of(getBookObject()));
        mockMvc.perform(put("/book/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getBookObject())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getBookObject())));
    }

    @Test
    public void update_WithBookServiceReturnEmptyOptional_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(bookService.update(anyInt(), any(Book.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/book/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getBookObject())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("New book data is null or book id is not found in the database"));
    }

    @Test
    public void update_WithInvalidId_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(bookService.update(anyInt(), any(Book.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/book/update/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getBookObject())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithBookId_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(bookService.update(anyInt(), any(Book.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/book/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isBadRequest());
    }


    public Book getBookObject() {
        return new Book("Title", "56565", Genre.ACTION, null);
    }

}