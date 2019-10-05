package otus.spring.main.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import otus.spring.main.dao.Reader;

@DisplayName("Тест ExaminatorImplTest")
@SpringBootTest
class ExaminatorImplTest {
	
	@Autowired
	private Reader reader;
	
	@Autowired
	private Examinator exam;

	@DisplayName(" должен выводить вопросы экзамена")
	@Test
	void shouldPrintQuestionsOfTheExam() 
	{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    int questionNumber = reader.readQuestions().size();	  
	    
	    String input = "";
	    for(int i = 0; i < questionNumber; i++)
	    	input = input+"a\n";
	    				
	    InputStream  in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
	    exam.takeExam();

	    assertThat(outContent.toString()).asString().isNotEmpty();		
	}

	@DisplayName(" должен возвращать результаты экзамена")
	@Test
	void shouldPrintResultsOfTheExam() 
	{		
		exam.setFIO("some name");
		Object res = exam.getStringResult();
		assertThat(res).isInstanceOf(String.class);
		assertThat(res).asString().isNotEmpty();
	}
}
