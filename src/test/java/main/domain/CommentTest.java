package main.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Тест Comment ")
@SpringBootTest
public class CommentTest {
	
	@Autowired
	private Comment c;

	@DisplayName(" должен вывести текст комментария")
	@Test
	void shouldPrintComment()
	{
		c = new Comment(1L,"Name","Comment", 1L);
		assertThat(c.toString()).matches(".*Comment");
	}
}
