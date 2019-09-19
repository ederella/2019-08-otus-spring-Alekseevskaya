package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import service.Question;

public class ReaderImpl implements Reader{
	private String path;
	
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
	
	public void setPath(String path)
	{
		this.path= path;
	}
	
}
