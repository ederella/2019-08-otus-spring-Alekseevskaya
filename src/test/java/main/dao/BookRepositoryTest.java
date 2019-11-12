package main.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@DisplayName("Тест BookRepository")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = { "main.dao" })
class BookRepositoryTest {

	@Autowired
	private BookRepository library;

	@Autowired
	private TestEntityManager em;

	@DisplayName(" должен возвращать список книг")

	@Test
	void shouldReturnAListOfBooks() {
		assertThat(library.findAll()).isNotEmpty();
		assertThat(library.findAll().get(0)).isInstanceOf(Book.class);
	}

	@DisplayName(" должен возвращать количество книг в библиотеке")

	@Test
	void shouldReturnCount() {
		long count = (Long) em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		assertThat(library.count()).isEqualTo(count);
	}

	/*
	 * @DisplayName(" должен удалить одну книгу")
	 * 
	 * @Test void shouldDeleteOneBook() { long count1 = (Long)
	 * em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").
	 * getSingleResult(); library.deleteById(0); long count2 = (Long)
	 * em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").
	 * getSingleResult(); assertThat(count2 - count1 == 1L); }
	 */

	@DisplayName(" должен вернуть книгу по id")

	@Test
	void shouldReturnBookById() {
		Book b = new Book(null, "Book Name", null);
		em.persist(b);
		long count = (Long) em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();

		Book b1 = library.findById(count);
		assertNotNull(b1);
		assertThat(b1.getBookName()).isEqualToIgnoringCase("Book Name");

	}

	@DisplayName(" должен установить заданное количество книг по id")

	@Test
	void shouldSetUpCertainNumberOfBook() {

		library.updateBookCountById(1L, 100500);
		int count = (Integer) em.getEntityManager().createQuery("SELECT b.count FROM Book b WHERE b.id =1L").getSingleResult();
		assertThat(count).isEqualTo(100500);
	}

}
