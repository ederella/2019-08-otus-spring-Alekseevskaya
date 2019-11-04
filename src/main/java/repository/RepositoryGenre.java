package repository;

import org.springframework.data.repository.CrudRepository;

import main.domain.Genre;

public interface RepositoryGenre extends CrudRepository<Genre, Integer>{

}
