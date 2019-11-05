package main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.domain.Comment;

public interface RepositoryComment extends CrudRepository<Comment, Long>{
	List<Comment> findAll();
	List<Comment> findByBookId(long bookId);
}
