package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Author;

@DisplayName("Тест AuthorDaoImpl")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = { "main.dao", "main.domain" })
public class AuthorDaoImplTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
	private AuthorDaoImpl db;
	@Mock
	private Author author;

	@DisplayName(" должен добавить автора в базу данных")
	@Test
	void shouldAddAuthor() {
		author = new Author("IVANOV", "IVAN", "");
		long count1 = (Long) em.getEntityManager().createQuery("SELECT COUNT(a) FROM Author a").getSingleResult();
		db.add(author);
		long count2 = (Long) em.getEntityManager().createQuery("SELECT COUNT(a) FROM Author a").getSingleResult();
		assertThat(count2 - count1 == 1L);
		List<Author> list = (List<Author>) em.getEntityManager().createQuery("SELECT a FROM Author a", Author.class).getResultList();
		assertThat(list).contains(author);
	}

	@DisplayName(" должен вернуть список всех авторов")
	@Test
	void shouldReturnListOfAuthors() {
		List<Author> list1 = db.findAll();
		List<Author> list2 = (List<Author>) em.getEntityManager().createQuery("SELECT a FROM Author a", Author.class).getResultList();
		assertThat(list1).containsAll(list2);

	}

	@DisplayName(" должен вернуть автора по id")
	@Test
	void shouldReturnAnAuthorByID() {
		author = (Author) em.getEntityManager().createQuery("SELECT a FROM Author a WHERE a.id=1L").getSingleResult();
		Author author1 = db.findById(1L);
		assertThat(author1).isEqualToComparingFieldByField(author);
	}

	@DisplayName(" должен удалить автора по id")
	@Test
	void shouldDeleteAnAuthorByID() {
		author = new Author("IVANOV", "IVAN", "");
		em.persist(author);
		long count1 = (Long) em.getEntityManager().createQuery("SELECT COUNT(a) FROM Author a").getSingleResult();
		db.deleteById(count1);
		long count2 = (Long) em.getEntityManager().createQuery("SELECT COUNT(a) FROM Author a").getSingleResult();
		assertThat(count1 - count2 == 1L);
		List<Author> list = (List<Author>) em.getEntityManager().createQuery("SELECT a FROM Author a", Author.class).getResultList();
		assertThat(list).doesNotContain(author);
	}
}
