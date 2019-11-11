package main.dao;

import java.util.List;

import main.domain.Book;
import main.domain.Comment;

public interface CommentDao {

	public void add(Comment comment);
	
	List<Comment> findByBook(Book book);
	
	int deleteById(long id);
}
