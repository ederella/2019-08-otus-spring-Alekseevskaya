package main.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTHORS")
public class Author {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private final long id;
	
	@Column(name="SURNAME")
	private final String surname;
	
	@Column(name="FIRSTNAME")
	private final String firstname;
	
	@Column(name="SECONDNAME")
	private final String secondname;

	public Author(String surname, String fisrtname, String secondname) {
		this.id = 0L;
		this.surname = surname;
		this.firstname = fisrtname;
		this.secondname = secondname;
	}
	
	public Author(long id, String surname, String fisrtname, String secondname) {
		this.id = id;
		this.surname = surname;
		this.firstname = fisrtname;
		this.secondname = secondname;
	}

	public Author() {
		this.id = 0L;
		this.surname = "";
		this.firstname = "";
		this.secondname = "";
	}
	
	public long getId() {
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
