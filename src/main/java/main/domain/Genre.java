package main.domain;


public class Genre {
	
	private String id;	
	private final String genreName;
	
	
	public Genre(long id, String genreName)
	{
		this.genreName = genreName;
	}
	
	public Genre()
	{
		this.genreName = "";
	}
	public Genre(String genreName)
	{
		this.genreName = genreName;
	}

	public String getGenreName() {
		return genreName;
	}

}
