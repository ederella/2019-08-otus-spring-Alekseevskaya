package repository;

import org.springframework.data.repository.CrudRepository;

import main.domain.Author;

public interface RepositoryAuthor extends CrudRepository<Author, Integer>{

}
