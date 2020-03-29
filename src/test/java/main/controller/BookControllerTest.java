package main.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import main.dao.BookRepository;
import main.security.AclConfig;
import main.security.AclGrants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {
	
	@Autowired
	BookController controller;
	
	@Autowired
	BookRepository repository;
	
	@Mock 
	Model model;
	
	@Test
	@WithMockUser(
    		username = "admin",
            authorities = "ROLE_ADMIN")
	public void shouldReturnListOfBooks() {
		String templateName = controller.listPage(model);
		assertThat(model.containsAttribute("books"));
		assertThat(templateName).isEqualToIgnoringCase("admin/library_list");
	}
	
	@Test
	@WithMockUser(
    		username = "admin",
            authorities = "ROLE_ADMIN")
	public void shouldReturnABookById() {
		long id = 1L;
		String templateName = controller.editPage(id, model);
		assertThat(model.containsAttribute("book"));
		assertThat(templateName).isEqualToIgnoringCase("admin/book_edit");
	}

	@Test
	@WithMockUser(
	    		username = "admin",
	            authorities = "ROLE_ADMIN")
	public void shouldUpdateDeleteBook() {
		long id = repository.findAll().get(0).getId();
		long count = repository.count();
		long[] authors = {1L};
		long[] genres = {1L};
		String name = "Name";
		controller.editBook(id, name, authors, genres, "save", model);	
		assertThat(repository.findById(id).getBookName().equalsIgnoreCase(name));
		controller.editBook(id, name, authors, genres, "delete", model);
		assertThat(count - repository.count() == 1L);
	}

	@Test
	@WithMockUser(
    		username = "admin",
            authorities = "ROLE_ADMIN")
	public void shouldAddAuthorsAndGenresToModelAndReturnTemplate() {
		String templateName = controller.createPage(model);
		assertThat(model.containsAttribute("authors"));
		assertThat(model.containsAttribute("genres"));
		assertThat(templateName).isEqualToIgnoringCase("admin/book_create");		
	}
	
	@Test
	@WithMockUser(
    		username = "admin",
            authorities = "ROLE_ADMIN")
	public void shouldAddNewBook() {
		long count = repository.count();
		long[] authors = {1L};
		long[] genres = {1L};
		String name = "Name";
		controller.createBook(name, authors, genres, model);
		assertThat(repository.count() == count + 1);
	}
}
