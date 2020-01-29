package main.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Book ")
@SpringBootTest
public class BookTest {

	Book book;

	@MockBean
	Author author;
	
	@MockBean
	Genre genre;

	@DisplayName(" должен заполнять поля данными и возвращать их")
	@Test
	public void shouldFillFieldsAndReturnThem() {
		List<Author> a = new ArrayList<Author>();
		a.add(author);
		List<Genre> s = new ArrayList<Genre>();
		s.add(genre);
		book = new Book(1, a, "name", s, 10);

		assertThat(book.getAuthors() == a);
		assertThat(book.getGenres() == s);
		assertThat(book.getBookName().equalsIgnoreCase("name"));
		assertThat(book.getId() == 1);
		assertThat(book.getCount() == 10);
	}

	@DisplayName(" должен устанавливать количество доступных книг")
	@Test
	public void shouldSetUpNumberOfAvailableBooks() {
		book = new Book(null, "name", null);
		book.setCount(100);
		assertThat(book.getCount() == 100);
	}

	@DisplayName(" должен возвращать информацию о книге")
	@Test
	public void shouldReturnAllInfoAboutBook() {
		book = new Book(null, "name", null);
		Object res = book.toString();
		assertThat(res).isInstanceOf(String.class);
	}
}
