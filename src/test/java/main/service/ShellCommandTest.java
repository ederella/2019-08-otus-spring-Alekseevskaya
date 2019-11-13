package main.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Book;
import main.domain.Comment;

@DisplayName("Тест команд shell ")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ShellCommandTest {

	@Autowired
	private Shell shell;

	@MockBean
	private AuthorServices authorServices;
	@MockBean
	private BookServices bookServices;
	@MockBean
	private CommentServices commentServices;
	@MockBean
	private Book book;	
	@MockBean
	private Comment comment;

	private static final String LIST_ALL = "ls";
	private static final String COUNT_ALL = "c";
	private static final String DELETE_BOOK = "d";
	private static final String GIVE_BOOK = "g";
	private static final String RETURN_BOOK = "r";
	private static final String SET_COUNT = "n";
	private static final String GET_BY_ID = "id";
	private static final String LIST_AUTHORS = "lsa";
	private static final String ADD_ANON_COMMENT = "cma";
	private static final long ID = 1;
	private static final String COMMENT = "Comment";
	private static final int COUNT = 1000;

	@DisplayName(" должен вызывать getAll для команды ls")
	@Test
	void shouldCallGetAllOnLs() {
		shell.evaluate(() -> LIST_ALL);
		org.mockito.Mockito.verify(bookServices, times(1)).printAllBooksInfo();
	}

	@DisplayName(" должен вызывать count для команды c")
	@Test
	void shouldCallCountOnC() {
		Object res = shell.evaluate(() -> COUNT_ALL);
		assertThat(res).isInstanceOf(String.class);
		org.mockito.Mockito.verify(bookServices, times(1)).getCountOfBookTypes();
	}

	@DisplayName(" должен вызывать deleteBookById для команды d")
	@Test
	void shouldCallDeleteBookOnD() throws Exception {
		shell.evaluate(() -> DELETE_BOOK + " " + ID);
		org.mockito.Mockito.verify(bookServices, times(1)).deleteBookById(ID);
	}

	@DisplayName(" должен вызывать giveBook для команды g")
	@Test
	void shouldCallGiveBookOnG() {
		shell.evaluate(() -> GIVE_BOOK + " " + ID);
		org.mockito.Mockito.verify(bookServices, times(1)).giveBook(ID);
	}

	@DisplayName(" должен вызывать returnBook для команды r")
	@Test
	void shouldCallReturnBookOnR() {
		shell.evaluate(() -> RETURN_BOOK + " " + ID);
		org.mockito.Mockito.verify(bookServices, times(1)).returnBook(ID);
	}

	@DisplayName(" должен вызывать setNumberOfBooks для команды n")
	@Test
	void shouldCallSetNumberOfBooksOnN() {
		shell.evaluate(() -> SET_COUNT + " " + ID + " " + COUNT);
		org.mockito.Mockito.verify(bookServices, times(1)).setNumberOfBooks(ID, COUNT);
	}

	@DisplayName(" должен вызывать getById для команды id")
	@Test
	void shouldCallGetByIdOnId() throws Exception {
		shell.evaluate(() -> GET_BY_ID + " " + ID);
		org.mockito.Mockito.verify(bookServices, times(1)).printBookById(ID);
	}
	
	@DisplayName(" должен вызывать listAuthors для команды lsa")
	@Test
	void shouldCallListAuthorsOnLsa() {
		shell.evaluate(() -> LIST_AUTHORS);
		org.mockito.Mockito.verify(authorServices, times(1)).printAllAuthors();
	}
}
