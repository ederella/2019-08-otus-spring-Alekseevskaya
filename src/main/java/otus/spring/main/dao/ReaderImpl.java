package otus.spring.main.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import otus.spring.main.service.Question;

@Service
public class ReaderImpl implements Reader
{
	private String path;	
	
	public ReaderImpl(@Value("${questions.path.ru}") String pathRU, 
			@Value("${questions.path.en}") String pathDefault,
			@Value("${user.locale}")Locale locale) 
	{
		if(locale.getLanguage().equalsIgnoreCase("ru"))
			this.path = pathRU;
		else
			this.path = pathDefault;
	}

	@Override
	public ArrayList<Question> readQuestions() 
	{	
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
		
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		String line = "";
		String cvsSplitBy = ";";
		ArrayList<Question> questionList = new  ArrayList<Question>();
		
		 try {
			 
			 while ((line = reader.readLine()) != null) 
			 {
			     String[] word = line.split(cvsSplitBy);			     
			    
			     String questionText = "";

			     for (int i = 0; i < word.length-1; i++) 
			     {
			    	questionText = questionText + '\n' + word[i];
			     }
				
			     if(questionText.length() > 0)
			     {
			    	 Question question = new Question(questionText, word[word.length-1]);
			    	 questionList.add(question);
			     }
			 }			 
		} 
		 catch (IOException e) 
		 {			
			e.printStackTrace();
		 }		
		
		return questionList;
	}
	
	@Override
	public void setPath(String path)
	{
		this.path= path;
	}
	
	
}
