package main.dao;

import java.util.List;

import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;

public interface LibraryDao {
	long count();

	List<Book> getAll();
	
	List<Author> listAuthors();

	void addBook(Book book);

	void deleteBookById(long id);

	Book getById(long id);

	boolean giveBook(long id);

	boolean returnBook(long id);

	boolean setNumberOfBooks(long id, int count);
	
	boolean addComment(Comment comment);
	
	boolean addAnonimousComment(String comment,long bookId);
	
	List<Comment> getCommentsForBook(long id);
	
}
