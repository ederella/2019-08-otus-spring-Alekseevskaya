package main.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Тест Author ")
@SpringBootTest
class AuthorTest {

	private Author author;

	@DisplayName(" должен заполнять поля данными и возвращать их")
	@Test
	void shouldFillFieldsAndReturnThem() {
		author = new Author("A", "B", "C");
		assertThat(author.getSurname().equalsIgnoreCase("A"));
		assertThat(author.getFirstname().equalsIgnoreCase("B"));
		assertThat(author.getSecondname().equalsIgnoreCase("C"));
	}

	@DisplayName(" должен возвращать ФИО автора в строку")
	@Test
	void shouldPrintAuthorFullName() {
		author = new Author("A", "B", "C");
		assertThat(author.toString()).matches("A B C");
	}
}
