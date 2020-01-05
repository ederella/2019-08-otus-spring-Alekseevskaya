package main.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import main.domain.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String>{
}
