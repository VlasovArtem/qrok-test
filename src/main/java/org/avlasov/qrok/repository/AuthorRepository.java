package org.avlasov.qrok.repository;

import org.avlasov.qrok.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by artemvlasov on 10/07/2017.
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);

}
