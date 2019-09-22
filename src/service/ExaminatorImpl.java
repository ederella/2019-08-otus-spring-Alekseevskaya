package service;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import dao.Reader;
import service.Question;

@Service
public class ExaminatorImpl implements Examinator
{	
	private Reader reader;
	private String fio;
	private int result;
	private int minimumRightAnswers;
	private ExamineMessageSource messageSource;
	
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
	public void takeExam() 
	{		
		Scanner sc = new Scanner(System.in);
		try
		{
			ArrayList<Question> ql = reader.readQuestions();
			
			if(ql.size()>0)
			{				
				System.out.println(messageSource.getMessage("user.askname"));
				this.fio = sc.nextLine();
							
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
		{
			sc.close();
		}

	}
	
	@Override
	public void printResult ()
	{
		System.out.println("");
		System.out.println("**************" + messageSource.getMessage("result.header") +"*******************");
		System.out.println("");
		
		if(!this.fio.isEmpty())
		 System.out.println(messageSource.getMessage("result.studentname",fio));
		 System.out.println(messageSource.getMessage("result.points", String.valueOf(this.result)));
		
		if(this.minimumRightAnswers > 0)
		{
			if(this.result >= minimumRightAnswers)
				System.out.println(messageSource.getMessage("result.exampassed"));
			else
				System.out.println(messageSource.getMessage("result.examfailed"));
		}
		System.out.println("");
		System.out.println("****************************************");

	}
	
	

}
