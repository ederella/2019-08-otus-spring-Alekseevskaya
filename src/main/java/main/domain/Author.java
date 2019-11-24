package main.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "authors")
public class Author {
	@Id
	private String id;
	
	@Field(value = "surname")
	private final String surname;
	
	@Field(value = "name")
	private final String firstname;
	
	@Field(value = "secondname")
	private final String secondname;

	public Author(String surname, String fisrtname, String secondname) {
		this.surname = surname;
		this.firstname = fisrtname;
		this.secondname = secondname;
	}
	
	public Author(long id, String surname, String fisrtname, String secondname) {
		this.surname = surname;
		this.firstname = fisrtname;
		this.secondname = secondname;
	}

	public Author() {
		this.surname = "";
		this.firstname = "";
		this.secondname = "";
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSurname() {
		return this.surname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getSecondname() {
		return this.secondname;
	}

	@Override
	public String toString() {
		String divider1 = "";
		String divider2 = "";

		if (this.surname.length() > 0 && this.firstname.length() > 0) {
			divider1 = " ";
		}
		if (this.firstname.length() > 0 && this.secondname.length() > 0) {
			divider2 = " ";
		}
		return this.surname + divider1 + this.firstname + divider2 + this.secondname;

	}
}
