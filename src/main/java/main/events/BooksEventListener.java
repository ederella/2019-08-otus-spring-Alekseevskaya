package main.events;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import main.dao.BookRepository;
import main.dao.CommentRepository;
import main.domain.Book;
import main.domain.Comment;

@Component
public class BooksEventListener extends AbstractMongoEventListener<Book> {

	private final BookRepository bookRepository;
	private final CommentRepository commentRepository;

	@Autowired
	public BooksEventListener(BookRepository bookRepository, CommentRepository commentRepository) {
		this.bookRepository = bookRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
		super.onBeforeDelete(event);
		Book b = bookRepository.findById(event.getSource().getObjectId("_id").toString()).get();
		List<Comment> comments = commentRepository.findByBook(b);
		if (comments != null) {
			comments.forEach((comment) -> {
				commentRepository.delete(comment);
			});
		}

	}
}
