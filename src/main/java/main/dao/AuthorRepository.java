package main.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{
	
	List<Author> findAll();

	Author findById(long id);
	
	Author findBySurnameAndFirstnameAndSecondname(String surname, String firstname, String secondname);
}
