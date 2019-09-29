package otus.spring.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Application {

	public static void main(String[] args) {
		otus.spring.main.service.Examinator exam =
		 SpringApplication.run(Application.class, args)
		 .getBean(otus.spring.main.service.Examinator.class);
		
		exam.takeExam();
		exam.printResult();

		
	}

}
