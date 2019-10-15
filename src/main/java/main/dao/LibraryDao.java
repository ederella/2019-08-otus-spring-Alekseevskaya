package main.dao;

import java.util.List;

import main.domain.Book;

public interface LibraryDao 
{    
	int count();

    List<Book> getAll();
    
    void addBook(Book book); 
    
    void deleteBookById(long id);
    
    Book getById(long id);
    
    void giveBook(long id);
    
    void returnBook(long id);

	void setNumberOfBooks(long id, int count);
}
