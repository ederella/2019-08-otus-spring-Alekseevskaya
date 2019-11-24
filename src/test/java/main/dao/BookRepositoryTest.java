package main.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Book;

@DisplayName("Тест BookRepository")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ComponentScan(basePackages = { "main.dao" })
class BookRepositoryTest {

	@Autowired
	private BookRepository library;

	@DisplayName(" должен возвращать список книг")
	@Test
	void shouldReturnAListOfBooks() {
		assertThat(library.findAll()).isNotEmpty();
		assertThat(library.findAll().get(0)).isInstanceOf(Book.class);
	}

	@DisplayName(" должен возвращать количество книг в библиотеке")
	@Test
	void shouldReturnCount() {
		assertThat(library.count()).isEqualTo(1);
	}

	@DisplayName(" должен вернуть книгу по id")
	@Test
	void shouldReturnBookById() {
		Book b = library.findAll().get(0);
		String id = b.getId();
		Book b1 = library.findById(id).get();
		assertNotNull(b1);
		assertThat(b1.getBookName().matches(b.getBookName()));
	}

	@DisplayName(" должен установить заданное количество книг по id")
	@Test
	void shouldSetUpCertainNumberOfBook() {
		String id = library.findAll().get(0).getId();
		library.updateBookCountById(id, 100500);
		int count = library.findById(id).get().getCount();
		assertThat(count).isEqualTo(100500);
	}

}
