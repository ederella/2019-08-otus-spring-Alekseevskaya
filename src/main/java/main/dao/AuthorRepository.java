package main.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import main.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String>{
	
	List<Author> findAll();
	
	Author findBySurnameAndFirstnameAndSecondname(String surname, String firstname, String secondname);
}
