package main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(name ="BOOKID")
	private final long bookId;

	public Comment(long id, String readerNickName, String commentText, long bookId) {
		this.id = id;
		this.setReaderNickName(readerNickName);
		this.setCommentText(commentText);
		this.bookId = bookId;
	}
	public Comment(String readerNickName, String commentText, long bookId) {
		this.id = 0L;
		this.setReaderNickName(readerNickName);
		this.setCommentText(commentText);
		this.bookId = bookId;
	}
	
	public Comment(String commentText, long bookId) {
		this.id = 0L;
		this.setCommentText(commentText);
		this.bookId = bookId;
	}
	
	public Comment()
	{
		this.id = 0L;
		this.bookId = 0L;
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

	public long getBookId() {
		return this.bookId;
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
