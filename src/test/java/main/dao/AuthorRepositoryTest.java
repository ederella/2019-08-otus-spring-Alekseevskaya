package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import main.domain.Author;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "main.dao", "main.domain" })
public class AuthorRepositoryTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
	private AuthorRepository db;
	@Mock
	private Author author;

	@Test
	public void shouldFindAuthorByFullName() {
		Object author = db.findBySurnameAndFirstnameAndSecondname("Ильф", "Илья", "Арнольдович");
		assertThat(author).isInstanceOf(Author.class);
		Author authorDB = (Author) em.getEntityManager().createQuery("SELECT a FROM Author a WHERE a.surname='Ильф' AND a.firstname='Илья' AND a.secondname='Арнольдович'").getSingleResult();
		assertThat((Author)author).isEqualToComparingFieldByField(authorDB);
	}

	//@DisplayName(" должен вернуть список всех авторов")
	@Test
	public void shouldReturnListOfAuthors() {
		List<Author> list1 = db.findAll();
		List<Author> list2 = (List<Author>) em.getEntityManager().createQuery("SELECT a FROM Author a", Author.class).getResultList();
		assertThat(list1).containsAll(list2);

	}

	//@DisplayName(" должен вернуть автора по id")
	@Test
	public void shouldReturnAnAuthorByID() {
		author = (Author) em.getEntityManager().createQuery("SELECT a FROM Author a WHERE a.id=1L").getSingleResult();
		Author author1 = db.findById(1L);
		assertThat(author1).isEqualToComparingFieldByField(author);
	}

}
