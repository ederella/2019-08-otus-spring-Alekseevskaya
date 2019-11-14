package main.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.AuthorRepository;
import main.domain.Author;
@Service
public class AuthorServices {

	private final AuthorRepository authorRepository;
	
	@Autowired	
	public AuthorServices(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public void addBookAuthors(List<Author> authors) {
		Author authorFromDB = null;
		if (authors != null) {
			for (Author author : authors) {
				authorFromDB = authorRepository.findBySurnameAndFirstnameAndSecondname(author.getSurname(),
						author.getFirstname(), author.getSecondname());
				if (authorFromDB != null) {
					authors.set(authors.indexOf(author), authorFromDB);
				} else
					authorRepository.save(author);
			}
		}
	}

	public String printAllAuthors() {
		StringBuilder sb = new StringBuilder();
		List<Author> authors = authorRepository.findAll();
		int i = 1;
		for (Author author : authors) {
			sb.append(i + ". ");
			sb.append(author.toString());
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}
}
