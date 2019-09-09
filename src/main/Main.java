package main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.Reader;
import service.Examinator;
import service.ExaminatorImpl;

public class Main
{

	public static void main(String[] args) 
	{
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("resources/spring-context.xml");
			Reader reader = context.getBean(Reader.class);
			Examinator exam = new ExaminatorImpl(reader);
			exam.takeExam();
			exam.printResult();
			context.close();
	}

}

