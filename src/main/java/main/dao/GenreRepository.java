package main.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;

import main.domain.Genre;
import reactor.core.publisher.Mono;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String>{

	Mono<Genre> findByGenreName(String genreName);

}
