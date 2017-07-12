package org.avlasov.qrok.service;

import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.AuthorShort;
import org.avlasov.qrok.entity.Reward;

import java.util.List;
import java.util.Optional;

/**
 * Created by artemvlasov on 11/07/2017.
 */
public interface AuthorService {

    List<Author> findAll();
    Optional<Author> findById(int id);
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);
    Optional<Author> add(Author author);
    Optional<Author> update(int id, Author source);
    Optional<Author> addAuthorReward(int id, Reward reward);
    Optional<List<Reward>> findAuthorRewards(int id);
    Optional<AuthorShort> findAuthorShort(int id);
    boolean delete(int id);

}
