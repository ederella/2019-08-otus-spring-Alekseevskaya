package main.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Author ")
@SpringBootTest
class AuthorTest {
	@Autowired
	private Author author;

	@DisplayName(" должен заполнять поля данными и возвращать их")
	@Test
	void shouldFillFieldsAndReturnThem() {
		author = new Author("A", "B","C");
		assertThat(author.getSurname().equalsIgnoreCase("A"));
		assertThat(author.getFirstname().equalsIgnoreCase("B"));
		assertThat(author.getSecondname().equalsIgnoreCase("C"));
	}

	@DisplayName(" должен возвращать ФИО автора в строку")
	@Test
	void shouldPrintAuthorFullName() {
		author = new Author("A", "B","C");
		Object res = author.toString();
		assertThat(res).isInstanceOf(String.class);
	}
}
