package otus.spring.main.service;

import java.util.ArrayList;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import otus.spring.main.dao.Reader;
import otus.spring.main.service.Question;

@Service
public class ExaminatorImpl implements Examinator
{	
	private final Reader reader;
	private String fio;
	private int result;
	private int minimumRightAnswers;
	private final ExamineMessageSource messageSource;
	
	public ExaminatorImpl(Reader reader,
			@Value("${questions.minimum}")int minimumRightAnswers,
			ExamineMessageSource messageSource)
	{
		this.reader = reader;
		this.fio = null;
		this.result = 0;
		this.minimumRightAnswers = minimumRightAnswers;
		this.messageSource = messageSource;
	}
	
	@Override
	public void setMinimumRightAnswers(int minimumRightAnswers)
	{
		this.minimumRightAnswers = minimumRightAnswers;
	}
	
	@Override
	public void setFIO(String fio)
	{
		this.fio = fio;
	}
	
	@Override
	public void takeExam() 
	{		
			Scanner sc = new Scanner(System.in);
		try
		{
			ArrayList<Question> ql = reader.readQuestions();

			if(ql.size()>0)
			{							
				System.out.println(messageSource.getMessage("questions.header"));
		
				for (int i = 0 ; i< ql.size(); i++)
				{
					System.out.println(ql.get(i).getQuestion());
					System.out.println(messageSource.getMessage("answer.header"));
					
					if(ql.get(i).chekAnswer( sc.nextLine()))
					{
						this.result ++;
						System.out.println(messageSource.getMessage("answer.correct"));
					}
					else
					{
						System.out.println(messageSource.getMessage("answer.incorrect", ql.get(i).getRightAnswer()));
					}
				}
			}
			else
			{
				System.out.println(messageSource.getMessage("questions.notfound"));
			}
		}
		catch(Exception e)
		{}
		finally
		{}

	}

	@Override
	public String getStringResult ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("**************" + messageSource.getMessage("result.header") +"*******************");
		sb.append("\n");
		
		if(!this.fio.isEmpty())
			sb.append(messageSource.getMessage("result.studentname", this.fio)+"\n");
		
		sb.append(messageSource.getMessage("result.points", String.valueOf(this.result)));
		sb.append("\n");
		
		if(this.minimumRightAnswers > 0)
		{
			if(this.result >= minimumRightAnswers)
				sb.append(messageSource.getMessage("result.exampassed"));
			else
				sb.append(messageSource.getMessage("result.examfailed"));
			sb.append("\n");
		}
		
		sb.append("****************************************");
		
		return sb.toString();
	}
}
