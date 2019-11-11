package main.dao;

import java.util.List;

import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;

public interface LibraryDao {	

	void add(Book book);
	
	List<Book> findAll();

	Book findById(long id);

	int deleteById(long id);

	boolean updateBookCountById(long id, int count);
	
	long count();
}
