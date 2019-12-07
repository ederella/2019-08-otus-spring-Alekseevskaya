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

@DisplayName("Тест AuthorRepository")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = { "main.dao", "main.domain" })
public class AuthorRepositoryTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
	private AuthorRepository db;
	@Mock
	private Author author;

	@DisplayName(" должен найти автора по ФИО")
	@Test
	void shouldFindAuthorByFullName() {
		Object author = db.findBySurnameAndFirstnameAndSecondname("Ильф", "Илья", "Арнольдович");
		assertThat(author).isInstanceOf(Author.class);
		Author authorDB = (Author) em.getEntityManager().createQuery("SELECT a FROM Author a WHERE a.surname='Ильф' AND a.firstname='Илья' AND a.secondname='Арнольдович'").getSingleResult();
		assertThat((Author)author).isEqualToComparingFieldByField(authorDB);
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

}
