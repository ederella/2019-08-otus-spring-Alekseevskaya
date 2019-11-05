package main.repository;

import org.springframework.data.repository.CrudRepository;

import main.domain.Genre;

public interface RepositoryGenre extends CrudRepository<Genre, Long>{

	Genre findByGenreName(String genreName);

}
