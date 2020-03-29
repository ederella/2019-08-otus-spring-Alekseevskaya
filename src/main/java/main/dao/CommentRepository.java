package main.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import main.domain.Book;
import main.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	List<Comment> findByBook(Book book);

}
