package main.controller;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import main.domain.Author;
import main.domain.Book;

@Projection(name = "inlineAuthors", types = { Book.class }) 
public interface InlineAuthors {

	String getBookName();
	List<String> getGenreNames();
	List<Author> getAuthors();
}
