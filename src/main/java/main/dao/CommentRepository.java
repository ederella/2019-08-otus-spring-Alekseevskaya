package main.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.domain.Book;
import main.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByBook(Book book);

}
