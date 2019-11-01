package main.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Book;
import main.domain.Comment;

@DisplayName("Тест LibraryDaoImpl")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = {"main.dao"})
class LibraryDaoImplTest {
	
	@Autowired
	private LibraryDao library;
	
	@Autowired
	private TestEntityManager em;
	

	@DisplayName(" должен возвращать список книг")
	@Test
	void shouldReturnAListOfBooks() {
		assertThat(library.getAll()).isNotEmpty();
		assertThat(library.getAll().get(0)).isInstanceOf(Book.class);		
	}
	
	@DisplayName(" должен добавить книгу в библиотеку")
	void shouldAddABookToTheLibrary()
	{
		long count1 = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		Book b = new Book(null, "Book Name", null);	
		library.addBook(b);
		long count2 = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		
		assertThat(count2-count1 == 1L);
	}
	
	@DisplayName(" должен возвращать количество книг в библиотеке")
	@Test
	void shouldReturnCount() {
		long count = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		assertThat(library.count()).isEqualTo(count);
	}
	
	@DisplayName(" должен удалить одну книгу")
	@Test
	void shouldDeleteOneBook() {
		long count1 = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		library.deleteBookById(0);
		long count2 = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		assertThat(count2-count1 == 1L);
	}
	
	@DisplayName(" должен вернуть книгу по id")
	@Test
	void shouldReturnBookById() {
		Book b = new Book(null, "Book Name", null);	
		em.persist(b);
		long count = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		
		Book b1 =library.getById(count);
		assertNotNull(b1);
		assertThat(b1.getBookName()).isEqualToIgnoringCase("Book Name");

	}

	@DisplayName(" должен уменьшить количество книг на 1")
	@Test
	void shouldDecreaseNumberOfBook() {
		long count = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		library.returnBook(1L);
		assertThat(library.getById(1L).getCount()).isEqualTo(count-1);
	}
	
	@DisplayName(" должен увеличить количество книг на 1")
	@Test
	void shouldIncreaseNumberOfBook() {
		
		long count = (Long)em.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
		library.giveBook(1L);
		assertThat(library.getById(1L).getCount()).isEqualTo(count+1);
	}

	/*@DisplayName(" должен установить заданное количество книг по id")
	@Test
	void shouldSetUpCertainNumberOfBook() {

		library.setNumberOfBooks(1L, 100500);
		long count = (Long)em.getEntityManager()
				.createQuery("SELECT COUNT(b) FROM Book b WHERE b.id =:id")
				.setParameter("id", 1L)
				.getSingleResult();
		assertThat(count).isEqualTo(100500);
	}*/
	@DisplayName(" должен добавить и прочитать комментарий к книге")
	@Test
	void shouldAddAComment() {

		Comment comment = new Comment("Reader", "Comment1", 1L);
		library.addComment(comment);
		assertNotNull(library.getCommentsForBook(1L));
		assertThat(library.getCommentsForBook(1L).size() == 1);
		
		library.addAnonimousComment("Comment2", 1L);
		assertThat(library.getCommentsForBook(1L).size() == 2);
		
		List<Comment> l = library.getCommentsForBook(1L);
		assertThat(l.get(0).getCommentText()).matches("Comment1");
		assertThat(l.get(0).getReaderNickName()).matches("Reader");
		assertThat(l.get(1).getCommentText()).matches("Comment2");
	}
}
