package main.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;
import main.domain.Genre;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@DisplayName("Тест RepositoryLibrary")
@ComponentScan(basePackages = { "main.repository", "main.domain" })
public class RepositoryLibraryTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private RepositoryLibrary library;

	private long bookId = 1L;

	@DisplayName(" должен добавить комментарий")
	@Test
	void shouldAddAComment() {
		long bookId = 1L;
		String sql = "SELECT c FROM Comment c where c.bookId=:bookId";
		TypedQuery<Comment> query = em.getEntityManager().createQuery(sql, Comment.class);
		query.setParameter("bookId", bookId);
		List<Comment> comments = query.getResultList();

		library.addAnonimousComment("Comment1", bookId);
		List<Comment> commentsAfterAddition1 = query.getResultList();
		assertThat(commentsAfterAddition1.size()).isEqualTo(comments.size() + 1);

		Comment c = new Comment("Ivan", "Comment2", bookId);
		library.addComment(c);
		List<Comment> commentsAfterAddition2 = query.getResultList();
		assertThat(commentsAfterAddition2.size()).isEqualTo(comments.size() + 2);
		assertThat(commentsAfterAddition2).contains(c);
	}

	@DisplayName(" должен добавить книгу")
	@Test
	void shouldAddABook() {
		String sql = "SELECT COUNT(b) FROM Book b";
		Query query = em.getEntityManager().createQuery(sql);
		long countBefore = (long) query.getSingleResult();

		Book book = new Book(new ArrayList<Author>(), "Book", new ArrayList<Genre>());
		library.addBook(book);

		long countAfter = (long) query.getSingleResult();

		assertThat(countAfter).isEqualTo(countBefore + 1);
	}

	@DisplayName(" должен вернуть количество книг в библиотеке")
	@Test
	void shouldReturnTotalNumberOfBooksInLibrary() {
		String sql = "SELECT COUNT(b) FROM Book b";
		Query query = em.getEntityManager().createQuery(sql);
		long count1 = (long) query.getSingleResult();

		long count2 = library.count();

		assertThat(count1).isEqualTo(count2);
	}

	@DisplayName(" должен удалить книгу по id")
	@Test
	void shouldDeleteBookById() {
		Book b = em.getEntityManager().find(Book.class, bookId);
		if (b != null) {
			try {
				library.deleteBookById(1L);

				Book b1 = em.getEntityManager().find(Book.class, bookId);

				assertThat(b1).isNull();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@DisplayName(" должен вернуть информацию обо всех книгах в библиотеке")
	@Test
	void shouldReturnInfoAboutAllBooksInLibrary() {
		String sql = "SELECT b FROM Book b";
		TypedQuery<Book> query = em.getEntityManager().createQuery(sql, Book.class);
		List<Book> books1 = query.getResultList();

		List<Book> books2 = library.getAll();

		assertThat(books1).containsAll(books2);

	}

	@DisplayName(" должен вернуть информацию о книге по id")
	@Test
	void shouldReturnInfoAboutABookById() {
		Book b1 = em.getEntityManager().find(Book.class, bookId);

		Book b2 = library.getById(bookId);

		assertThat(b1).isEqualToComparingFieldByField(b2);
	}

	@DisplayName(" должен вернуть все комментарии по книге")
	@Test
	void shouldReturnAllCommentsOnBookById() {
		List<Comment> comments1 = library.getCommentsForBook(bookId);

		String sql = "SELECT c FROM Comment c where c.bookId=:bookId";
		TypedQuery<Comment> query = em.getEntityManager().createQuery(sql, Comment.class);
		query.setParameter("bookId", bookId);
		List<Comment> comments2 = query.getResultList();

		assertThat(comments1).containsAll(comments2);

	}

	@DisplayName(" должен уменьшить/увеличить количество книг определенного типа на 1 ")
	@Test
	void shouldIncreaseAndDecreaseNumberOfBooksById() {
		int bookCount1 = em.getEntityManager().find(Book.class, bookId).getCount();
		library.giveBook(bookId);
		int bookCount2 = em.getEntityManager().find(Book.class, bookId).getCount();
		assertThat(bookCount1).isEqualTo(bookCount2 + 1);

		library.returnBook(bookId);
		int bookCount3 = em.getEntityManager().find(Book.class, bookId).getCount();
		assertThat(bookCount2).isEqualTo(bookCount3 - 1);

	}

	@DisplayName(" должен вернуть список авторов по книге")
	@Test
	void shouldReturnAListOfAuthorsByBookId() {
		List<Author> authors1 = library.listAuthors();

		String sql = "SELECT a FROM Author a";
		TypedQuery<Author> query = em.getEntityManager().createQuery(sql, Author.class);
		List<Author> authors2 = query.getResultList();

		assertThat(authors1).containsAll(authors2);

	}

	@DisplayName(" должен установить количство книг определенного вида по id")
	@Test
	void shouldSetUpNumberOfBooksOfCertainType() {

		library.setNumberOfBooks(bookId, 1);

		Book b = em.getEntityManager().find(Book.class, bookId);

		assertThat(b.getCount()).isEqualTo(1);
	}

}
