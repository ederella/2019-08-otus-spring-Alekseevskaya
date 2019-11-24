package main.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import main.domain.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

	List<Book> findAll();
	
	int updateBookCountById(String id, int count);

	long count();

}
