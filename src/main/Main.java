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
			Examinator exam = context.getBean(Examinator.class);
			exam.takeExam();
			exam.printResult();
			context.close();
	}

}

