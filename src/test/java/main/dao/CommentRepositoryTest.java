package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Book;
import main.domain.Comment;

@DisplayName("Тест CommentRepository")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ComponentScan(basePackages = { "main.dao" })
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	BookRepository bookRepository;

	@DisplayName(" должен вернуть список комментариев по книге")
	@Test
	void shouldReturnAListOfComments() {
		Book b = bookRepository.findAll().get(0);
		Comment comment = commentRepository.findByBook(b).get(0);
		assertThat(comment.getCommentText().matches("TestComment") && comment.getReaderNickName().matches("TestReaderName"));
	}
}
