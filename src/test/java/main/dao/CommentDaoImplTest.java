package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Book;
import main.domain.Comment;

@DisplayName("Тест CommentDaoImpl")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = { "main.dao" })
public class CommentDaoImplTest {

	@Autowired
	CommentDao db;
	@Autowired
	TestEntityManager em;

	@DisplayName(" должен добавить комментарий к книге")
	@SuppressWarnings("unchecked")
	@Test
	void shouldAddAComment() {
		Book b = em.find(Book.class, 1L);
		Comment comment = new Comment("Reader", "Comment1", b);
		db.add(comment);

		List<Comment> comments = (List<Comment>) em.getEntityManager()
				.createQuery("SELECT c FROM Comment c WHERE c.book = :book").setParameter("book", b).getResultList();

		assertThat(comments).contains(comment);
	}

	@DisplayName(" должен удалить комментарий к книге")
	@Test
	void shouldDeleteAComment() {

		long idMax = (Long) em.getEntityManager().createQuery("SELECT MAX(c.id) FROM Comment c").getSingleResult();
		db.deleteById(idMax);
		long idNewMax = (Long) em.getEntityManager().createQuery("SELECT MAX(c.id) FROM Comment c").getSingleResult();
		assertThat(idMax - idNewMax == 1);
	}

	@DisplayName(" должен вернуть список комментариев по книге")
	@SuppressWarnings("unchecked")
	@Test
	void shouldReturnAListOfComments() {
		Book b = em.find(Book.class, 1L);

		List<Comment> comments = (List<Comment>) em.getEntityManager()
				.createQuery("SELECT c FROM Comment c WHERE c.book = :book").setParameter("book", b).getResultList();
		List<Comment> commentsB = db.findByBook(b);
		assertThat(comments).containsAll(commentsB);
	}
}
