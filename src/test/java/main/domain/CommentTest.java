package main.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {
	
	private Comment c;

	@DisplayName(" должен вывести текст комментария")
	@Test
	public void shouldPrintComment()
	{
		c = new Comment(1L,"Name","Comment", null);
		assertThat(c.toString()).matches(".*Comment");
	}
}
