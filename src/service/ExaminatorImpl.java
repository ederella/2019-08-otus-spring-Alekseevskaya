package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.Reader;
import service.Question;

public class ExaminatorImpl implements Examinator{
	
	private Reader reader;
	private String fio;
	private int result;
	
	public void setReader(Reader reader)
	{
		this.reader = reader;
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
				
				System.out.println("Enter your name");
				this.fio = sc.nextLine();
				
				
				System.out.println("Answer the questions below");
		
				for (int i = 0 ; i< ql.size(); i++)
				{
					System.out.println(ql.get(i).getQuestion());
					System.out.println("Your answer");
					
					if(ql.get(i).chekAnswer( sc.nextLine()))
					{
						this.result ++;
						System.out.println("Correct!");
					}
					else
					{
						System.out.println("Incorrect, the right answer is "+ ql.get(i).getRightAnswer());
					}
				}
			}
			else
			{
				System.out.println("Questions are not found");
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
		System.out.println("**************Results*******************");
		System.out.println("");
		if(!this.fio.isEmpty())
		 System.out.println("Student: "+fio+'\n'+ "Total result: "+ this.result + " points");
		System.out.println("");
		System.out.println("****************************************");

	}

}
