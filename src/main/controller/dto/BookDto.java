package main.controller.dto;

import main.domain.Book;

public class BookDto {

	private long id;
	private String screenName;
	private String editLink;
	
	BookDto(){}
	
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public static BookDto toDto(Book book) {
		BookDto dto = new BookDto();
		dto.setId(book.getId());
		dto.setScreenName(book.toString());
		dto.setEditLink("/edit?id=" + book.getId());
		return dto;
	}

}
