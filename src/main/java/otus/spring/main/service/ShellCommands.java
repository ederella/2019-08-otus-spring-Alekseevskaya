package otus.spring.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellCommands {
	
	private final Examinator exam;
	private boolean isLoginEntered;
	private boolean isExamHasBeenTaken;
	
	@Autowired
	public ShellCommands(Examinator exam)
	{
		this.exam = exam;
		isLoginEntered = false;
		isExamHasBeenTaken = false;
	}
	
	@ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "student") String userName) 
	{
		isLoginEntered = true;
		exam.setFIO(userName);
        return String.format("%s is successfully logged in", userName);
    }
	
	@ShellMethod(value = "Get the exam", key = {"e", "exam"})
	@ShellMethodAvailability(value = "isExamineAvailable")
	public void takeExam()
	{
		exam.takeExam();
		isLoginEntered = false;
		isExamHasBeenTaken = true;
	}
	
	@ShellMethod(value = "Get the result", key = {"r", "result"})
	@ShellMethodAvailability(value = "isResultAvailable")
	public String printResult()
	{
		isExamHasBeenTaken = false;
		return exam.getStringResult();		
	}
	private Availability isExamineAvailable() {
        return isLoginEntered? Availability.available(): Availability.unavailable("student name should be entered before passing the exam");
    }
	private Availability isResultAvailable() {
        return isExamHasBeenTaken? Availability.available(): Availability.unavailable("examine should be passed before getting the results");
    }
}
