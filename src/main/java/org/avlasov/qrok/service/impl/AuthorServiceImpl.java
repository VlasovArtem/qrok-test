package org.avlasov.qrok.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.avlasov.qrok.entity.Author;
import org.avlasov.qrok.entity.Reward;
import org.avlasov.qrok.repository.AuthorRepository;
import org.avlasov.qrok.service.AuthorService;
import org.avlasov.qrok.utils.AuthorUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger LOGGER = LogManager.getLogger(AuthorServiceImpl.class);
    private final AuthorRepository authorRepository;
    private final AuthorUpdater authorUpdater;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorUpdater authorUpdater) {
        this.authorRepository = authorRepository;
        this.authorUpdater = authorUpdater;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(int id) {
        return Optional.ofNullable(authorRepository.findOne(id));
    }

    @Override
    public Optional<Author> findByFirstName(String firstName) {
        return authorRepository.findByFirstName(firstName);
    }

    @Override
    public Optional<Author> findByLastName(String lastName) {
        return authorRepository.findByLastName(lastName);
    }

    @Override
    public Optional<Author> add(Author author) {
        return Optional.ofNullable(authorRepository.save(author));
    }

    @Override
    public Optional<Author> update(int id, Author source) {
        if (Objects.nonNull(source) && authorRepository.exists(id)) {
            return Optional.of(authorUpdater.update(authorRepository.findOne(id), source));
        } else {
            LOGGER.warn("Author is not found by id {} or source author is null.");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> addAuthorReward(int id, Reward reward) {
        if (Objects.nonNull(reward) && authorRepository.exists(id)) {
            Author author = authorRepository.findOne(id);
            List<Reward> rewards = Optional.ofNullable(author.getRewards())
                    .orElseGet(ArrayList::new);
            rewards.add(reward);
            author.setRewards(rewards);
            authorRepository.flush();
            return Optional.of(author);
        } else {
            LOGGER.warn("Author is not found by id {} or reward is null");
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        if (authorRepository.exists(id)) {
            authorRepository.delete(id);
            return true;
        }
        return false;
    }
}
