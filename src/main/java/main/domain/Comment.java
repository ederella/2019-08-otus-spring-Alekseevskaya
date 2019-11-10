package main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="COMMENTS") 
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private final long id;
	
	@Column(name ="READERNICKNAME")
	private String readerNickName;
	
	@Column(name ="COMMENTTEXT")
	private String commentText;
	
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name ="BOOKID")
	private final Book book;

	public Comment(long id, String readerNickName, String commentText, Book book) {
		this.id = id;
		this.setReaderNickName(readerNickName);
		this.setCommentText(commentText);
		this.book = book;
	}
	public Comment(String readerNickName, String commentText, Book book) {
		this.id = 0L;
		this.setReaderNickName(readerNickName);
		this.setCommentText(commentText);
		this.book = book;
	}
	
	public Comment(String commentText, Book book) {
		this.id = 0L;
		this.setCommentText(commentText);
		this.book = book;
	}
	
	public Comment()
	{
		this.id = 0L;
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
