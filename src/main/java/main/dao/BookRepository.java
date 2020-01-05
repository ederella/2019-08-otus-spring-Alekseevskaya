package main.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import main.domain.Book;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
