package org.avlasov.qrok.controller;

import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Reward;
import org.avlasov.qrok.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 12/07/2017.
 */
@RestController
@RequestMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/all")
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @RequestMapping(path = "/info/{author_id}", method = GET)
    public ResponseEntity getAuthor(@PathVariable("author_id") int id) {
        return authorService.findById(id)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id is not found"));

    }

    @RequestMapping(path = "/info/firstName/{firstName}", method = GET)
    public ResponseEntity getByFirstName(@PathVariable String firstName) {
        return authorService.findByFirstName(firstName)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with first name is not found"));
    }

    @RequestMapping(path = "/info/lastName/{lastName}", method = GET)
    public ResponseEntity getByLastName(@PathVariable String lastName) {
        return authorService.findByLastName(lastName)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with last name is not found"));
    }

    @RequestMapping(path = "/add", method = POST)
    public ResponseEntity add(@RequestBody Author author) {
        return authorService.add(author)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Author was not saved."));
    }

    @RequestMapping(path = "/update/{author_id}", method = PUT)
    public ResponseEntity update(@PathVariable("author_id") int id, @RequestBody Author author) {
        return authorService.update(id, author)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Author id is not found or author is empty."));
    }

    @RequestMapping(path = "/add/{author_id}/reward", method = POST)
    public ResponseEntity addReward(@PathVariable("author_id") int id, @RequestBody Reward reward) {
        return authorService.addAuthorReward(id, reward)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Author id is not found or reward is empty"));
    }

    @RequestMapping(path = "/info/{author_id}/rewards", method = GET)
    public ResponseEntity findAuthorRewards(@PathVariable("author_id") int id) {
        return authorService.findAuthorRewards(id)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author id is not found"));
    }

    @RequestMapping(path = "/info/short/{author_id}", method = GET)
    public ResponseEntity findAuthorShortInfo(@PathVariable("author_id") int id) {
        return authorService.findAuthorShort(id)
                .<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author id is not found"));
    }


}
