package main.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;

import main.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{
	
	@PostFilter("hasPermission(filterObject, 'READ')")
	List<Author> findAll();
	
	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Author findById(long id);
	
	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Author findBySurnameAndFirstnameAndSecondname(String surname, String firstname, String secondname);
}
