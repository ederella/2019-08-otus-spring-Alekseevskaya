package main.domain;

import java.util.Iterator;
import java.util.List;

public class Book 
{
	private final long id;
	private final List<Author> authors;
	private final String bookName;
	private final List<String> genres;
	private int count;
	
	public Book(List<Author> authors, String bookName, List<String> genres)
	{
		this.id = 0L;
		this.authors = authors;
		this.bookName = bookName;
		this.genres = genres;		
	}
	
	public Book(long id, List<Author> authors, String bookName, List<String> genres, int number)
	{
		this.id = id;
		this.authors = authors;
		this.bookName = bookName;
		this.genres = genres;	
		this.count = number;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
    public long getId() 
    {
        return id;
    }
    
    public List<Author> getAuthors() 
    {
        return authors;
    }
    
    public String getBookName() 
    {
        return bookName;
    }
    
    public List<String> getGenres() 
    {
        return genres;
    }
    
    public int getCount() 
    {
        return count;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("id - " + this.id + "\n");
    	if(authors !=null)
    	{
	    	for (Iterator iterator = authors.iterator(); iterator.hasNext();)
	    	{
				Author author = (Author) iterator.next();	
				sb.append(author.toString());
				
				if(!iterator.hasNext())
					sb.append(": \n");
				else
					sb.append(", ");
			}
    	}
    	sb.append("   ~ " + this.bookName + " ~   \n");
    	
    	if(genres!=null)
    	{
	    	if(genres.size()>0)
	    		sb.append("(");
	    	for (Iterator iterator = genres.iterator(); iterator.hasNext();)
	    	{
				String genre = (String) iterator.next();
				sb.append(genre);
				
				if(!iterator.hasNext())
					sb.append(")\n");
				else
					sb.append(", ");
				
			}
    	}
    	sb.append(this.count + " шт.\n");
    	return sb.toString();
    }
}
