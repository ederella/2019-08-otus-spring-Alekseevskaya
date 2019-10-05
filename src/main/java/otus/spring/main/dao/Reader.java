package otus.spring.main.dao;

import java.util.ArrayList;
import otus.spring.main.service.Question;

public interface Reader {

	ArrayList<Question> readQuestions();

	void setPath(String path);
}
