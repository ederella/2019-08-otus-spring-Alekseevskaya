package main.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import main.dao.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerUserTest {

	@Autowired
	BookControllerUser controller;
	
	@Autowired
	BookRepository repository;
	
	@Mock 
	Model model;
	
	@Test
	public void shouldReturnListOfBooks() {
		String templateName = controller.listPage(model);
		assertThat(model.containsAttribute("books"));
		assertThat(templateName).isEqualToIgnoringCase("user/library_list");
	}
	
	@Test
	public void shouldGetAndReturnABook() {
		long id = repository.findAll().get(0).getId();
		long count1 = repository.findById(id).getCount();
		controller.userGetReturnBook(String.valueOf(id),"get", model);
		long count2 = repository.findById(id).getCount();		
		assertThat(count2 == count1+1);
		controller.userGetReturnBook(String.valueOf(id),"return", model);
		assertThat(repository.findById(id).getCount() == count1);
	}
}
