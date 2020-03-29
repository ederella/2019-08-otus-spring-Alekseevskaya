package main.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import main.security.AclConfig;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CommentTest {
	
	private Comment c;
	
    @WithMockUser(
    		username = "user",
            authorities = "ROLE_USER")
	@DisplayName(" должен вывести текст комментария")
	@Test
	public void shouldPrintComment()
	{
		c = new Comment(1L,"Name","Comment", null);
		assertThat(c.toString()).matches(".*Comment");
	}
}
