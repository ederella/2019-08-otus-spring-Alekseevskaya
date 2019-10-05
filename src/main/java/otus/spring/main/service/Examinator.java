package otus.spring.main.service;


public interface Examinator 
{
	void takeExam();
	String getStringResult ();
	void setMinimumRightAnswers(int minimumRightAnswers);
	void setFIO(String fio);
}
