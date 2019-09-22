package main;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import service.Examinator;
@Configuration
@ComponentScan(basePackages="dao, service, config")
@PropertySource("classpath:resources/application.properties")
public class Main
{
	public static void main(String[] args) 
	{
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(Main.class);	
			ctx.refresh();
			Examinator exam = ctx.getBean(Examinator.class);
			exam.takeExam();
			exam.printResult();
			ctx.close();
	}
}

