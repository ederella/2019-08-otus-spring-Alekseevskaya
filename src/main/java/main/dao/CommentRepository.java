package main.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import main.domain.Book;
import main.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {

	List<Comment> findByBook(Book book);

}
