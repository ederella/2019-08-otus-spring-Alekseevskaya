package main.domain;

public class Author 
{
	private final String surname;
	private final String fisrtname;
	private final String secondname;
	
	public Author(String surname, String fisrtname, String secondname)
	{
		this.surname = surname;
		this.fisrtname = fisrtname;
		this.secondname = secondname;
	}

	public String getSurname()
	{
		return this.surname;
	}
	public String getFirstname()
	{
		return this.fisrtname;
	}
	public String getSecondname()
	{
		return this.secondname;
	}
	@Override
	public String toString() 
	{
		String divider1 = "";
		String divider2 = "";

		if(this.surname.length()>0 && this.fisrtname.length()>0)
		{
			divider1 = " ";
		}
		if(this.fisrtname.length()>0 && this.secondname.length()>0)
		{
			divider2 = " ";
		}	
		return this.surname + divider1 +this.fisrtname + divider2 +this.secondname;

	}
}
