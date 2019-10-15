package main.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import main.domain.Author;
import main.domain.Book;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест LibraryDaoImpl")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(LibraryDaoImpl.class)
class LibraryDaoImplTest {

	@Autowired
	private LibraryDaoImpl library;
	
	@MockBean
	Book book;
	
	@MockBean
	Author author;
	
	@DisplayName(" должен возвращать список книг")
	@Test
	void shouldReturnAListOfBooks() {
		assertThat(library.getAll()).isNotEmpty();
		assertThat(library.getAll().get(0)).isInstanceOf(Book.class);
		assertThat(library.getAll().get(0).getBookName()).isEqualToIgnoringCase("Золотой теленок");
	}
	
	@DisplayName(" должен возвращать количество книг в базе")
	@Test
	void shouldReturnCount() {
		assertThat(library.count()).isEqualTo(6);
	}
	
	@DisplayName(" должен удалить одну книгу")
	@Test
	void shouldDeleteOneBook() {
		int c1 = library.count();
		library.deleteBookById(0);
		int c2 = library.count();
		assertThat(c1-c2 == 1);
	}
	
	@DisplayName(" должен вернуть книгу по id")
	@Test
	void shouldReturnBookById() {
		
		assertThat(library.getById(1L)).isInstanceOf(Book.class);
		assertThat(library.getById(1L).getBookName()).isEqualToIgnoringCase("Золотой теленок");
	}

	@DisplayName(" должен уменьшить количество книг на 1")
	@Test
	void shouldDecreaseNumberOfBook() {
		int c1 = library.getById(1L).getCount();
		library.giveBook(1L);
		assertThat(library.getById(1L).getCount()).isEqualTo(c1-1);
	}
	
	@DisplayName(" должен увеличить количество книг на 1")
	@Test
	void shouldIncreaseNumberOfBook() {
		
		int c1 = library.getById(1L).getCount();
		library.returnBook(1L);
		assertThat(library.getById(1L).getCount()).isEqualTo(c1+1);
	}

	@DisplayName(" должен установить заданное количество книг")
	@Test
	void shouldSetUpCertainNumberOfBook() {

		library.setNumberOfBooks(1L, 100500);
		assertThat(library.getById(1L).getCount()).isEqualTo(100500);
	}
}
