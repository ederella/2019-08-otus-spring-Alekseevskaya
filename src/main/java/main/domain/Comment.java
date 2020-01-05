package main.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comments")
public class Comment {
	@Id
	private String id;
	
	@Field(value = "readerNickName")
	private String readerNickName;
	
	@Field(value = "commentText")
	private String commentText;
	
	@DBRef
	private final Book book;

	public Comment( String readerNickName, String commentText, Book book) {
		this.setReaderNickName(readerNickName);
		this.setCommentText(commentText);
		this.book = book;
	}
	
	public Comment(String commentText, Book book) {
		this.setCommentText(commentText);
		this.book = book;
	}
	
	public Comment()
	{
		this.book = null;
	}
	public String getReaderNickName() {
		return readerNickName;
	}

	public void setReaderNickName(String readerNickName) {
		this.readerNickName = readerNickName;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Book getBook() {
		return this.book;
	}
	
	@Override
	public String toString() {
		if (commentText != null && commentText.trim().length() > 0) {
			if (readerNickName != null && readerNickName.trim().length() > 0) {
				return readerNickName + ": " + commentText;
			} else
				return "Anonimous: " + commentText;
		} else
			return "";
	}

}
