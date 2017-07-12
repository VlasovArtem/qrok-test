package org.avlasov.qrok.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.AuthorShort;
import org.avlasov.qrok.entity.Reward;
import org.avlasov.qrok.enums.Sex;
import org.avlasov.qrok.service.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artemvlasov on 12/07/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = AuthorController.class)
@WithMockUser(username = "qrok", password = "qrok-password")
public class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    public AuthorControllerTest() {
        this.mapper = Jackson2ObjectMapperBuilder
                .json()
                .defaultViewInclusion(true)
                .autoDetectFields(true)
                .build();
    }

    @Test
    public void getAll_WithValidaData_ReturnResponseEntityWithAuthor() throws Exception {
        List<Author> authors = Collections.singletonList(getAuthor());
        when(authorService.findAll()).thenReturn(authors);
        mockMvc.perform(get("/author/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));
    }

    @Test
    public void getAuthor_WithExistingAuthorId_ReturnResponseEntityWithAuthor() throws Exception {
        when(authorService.findById(anyInt())).thenReturn(Optional.of(getAuthor()));
        mockMvc.perform(get("/author/info/10"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthor())));
    }

    @Test
    public void getAuthor_WithNotExistingAuthorId_ReturnResponseEntityWithNotFoundStatusAndMessage() throws Exception {
        when(authorService.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/author/info/10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author with id is not found"));
    }

    @Test
    public void getByFirstName_WithExistingFirstName_ReturnResponseEntityWithAuthor() throws Exception {
        when(authorService.findByFirstName(anyString())).thenReturn(Optional.of(getAuthor()));
        mockMvc.perform(get("/author/info/firstName/first"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthor())));
    }

    @Test
    public void getByFirstName_WithNotExistingFirstName_ReturnResponseEntityWithNotFoundStatusAndMessage() throws Exception {
        when(authorService.findByFirstName(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/author/info/firstName/first"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author with first name is not found"));
    }

    @Test
    public void getByLastName_WithExistingLastName_ReturnResponseEntityWithAuthor() throws Exception {
        when(authorService.findByLastName(anyString())).thenReturn(Optional.of(getAuthor()));
        mockMvc.perform(get("/author/info/lastName/first"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthor())));
    }

    @Test
    public void getByLastName_WithNotExistingLastName_ReturnResponseEntityWithNotFoundStatusAndMessage() throws Exception {
        when(authorService.findByLastName(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/author/info/lastName/first"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author with last name is not found"));
    }

    @Test
    public void add_WithValidAuthor_ReturnResponseEntityWithAuthor() throws Exception {
        when(authorService.add(Mockito.any(Author.class))).thenReturn(Optional.of(getAuthor()));
        mockMvc.perform(post("/author/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getAuthor())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthor())));
    }

    @Test
    public void add_WithAddReturnEmpty_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        mockMvc.perform(post("/author/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void add_WithInvalidAuthor_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(authorService.add(any(Author.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/author/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getAuthor())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author was not saved."));
    }

    @Test
    public void update_WithValidaIdAndAuthor_ReturnResponseEntityWithAuthor() throws Exception {
        when(authorService.update(anyInt(), any(Author.class))).thenReturn(Optional.of(getAuthor()));
        mockMvc.perform(put("/author/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getAuthor())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthor())));
    }

    @Test
    public void update_WithAuthorServiceReturnEmptyOptional_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(authorService.update(anyInt(), any(Author.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/author/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getAuthor())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author id is not found or author is empty."));
    }

    @Test
    public void update_WithInvalidId_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(authorService.update(anyInt(), any(Author.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/author/update/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getAuthor())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithAuthorId_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(authorService.update(anyInt(), any(Author.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/author/update/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addReward_WithValidaIdAndReward_ReturnResponseEntityWithAuthor() throws Exception {
        when(authorService.addAuthorReward(anyInt(), any(Reward.class))).thenReturn(Optional.of(getAuthorWithReward()));
        mockMvc.perform(post("/author/add/10/reward")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getReward())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthorWithReward())));
    }

    @Test
    public void addReward_WithAuthorServiceReturnEmptyOptional_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(authorService.addAuthorReward(anyInt(), any(Reward.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/author/add/10/reward")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getReward())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author id is not found or reward is empty"));
    }

    @Test
    public void addReward_WithInvalidId_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(authorService.addAuthorReward(anyInt(), any(Reward.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/author/add/test/reward")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(getAuthor())))
                .andExpect(status().is(405));
    }

    @Test
    public void addReward_WithAuthorIdAndMissingContent_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(authorService.addAuthorReward(anyInt(), any(Reward.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/author/add/10/reward")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAuthorRewards_WithExistingId_ReturnResponseEntityWithListOfRewards() throws Exception {
        when(authorService.findAuthorRewards(anyInt())).thenReturn(Optional.of(Collections.singletonList(getReward())));
        mockMvc.perform(get("/author/info/10/rewards"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(getReward()))));
    }

    @Test
    public void findAuthorRewards_WithNotExistingId_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(authorService.findAuthorRewards(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/author/info/10/rewards"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author id is not found"));
    }

    @Test
    public void findAuthorRewards_WithIncorrectAuthorId_ReturnResponseEntityWithBadRequestStatus() throws Exception {
        when(authorService.findAuthorRewards(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/author/info/test/rewards"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAuthorShortInfo_WithExistingId_ReturnResponseEntityWithAuthorShort() throws Exception {
        when(authorService.findAuthorShort(anyInt())).thenReturn(Optional.of(getAuthorShort()));
        mockMvc.perform(get("/author/info/short/10"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getAuthorShort())));
    }

    @Test
    public void findAuthorShortInfo_WithNotExistingId_ReturnResponseEntityWithBadRequestStatusAndMessage() throws Exception {
        when(authorService.findAuthorShort(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/author/info/short/10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author id is not found"));
    }

    private Author getAuthor() {
        return new Author("first", "last", Sex.MALE, null, LocalDate.now(), null);
    }

    private Author getAuthorWithReward() {
        Author author = getAuthor();
        author.setRewards(Collections.singletonList(getReward()));
        return author;
    }

    public Reward getReward() {
        return new Reward(2017, "test");
    }

    private AuthorShort getAuthorShort() {
        return new AuthorShort("first", "last", 33, Collections.singletonList("Title"));
    }
}