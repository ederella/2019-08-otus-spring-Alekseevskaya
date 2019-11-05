package main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.domain.Author;

public interface RepositoryAuthor extends CrudRepository<Author, Long>{
	List<Author> findAll();

	Author findBySurnameAndFirstnameAndSecondname(String surname, String firstname, String secondname);
}
