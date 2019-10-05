package otus.spring.main.dao;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import otus.spring.main.service.Question;

@DisplayName("Тест Reader ")
@SpringBootTest
class ReaderImplTest {
	
	@Autowired
	private Reader reader;

	@DisplayName(" должен вернуть список вопросов")
	@Test
	void shouldCreateQuestions()
	{
		Object res = reader.readQuestions();
		assertThat(res).isInstanceOf(ArrayList.class);
		assertThat(res).asList().size().isGreaterThan(0);
		assertThat(res).asList().hasOnlyElementsOfType(Question.class);
	}
}
