package main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.domain.Book;

public interface RepositoryBook extends CrudRepository<Book, Long>{
	List<Book> findAll();
	Book findById(long id);
}
