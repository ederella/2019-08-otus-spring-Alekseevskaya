package main.dao;

import java.util.List;

import main.domain.Author;

public interface AuthorDao {
	
	void add(Author author);
	
	List<Author> findAll();

	Author findById(long id);

	int deleteById(long id);

}
