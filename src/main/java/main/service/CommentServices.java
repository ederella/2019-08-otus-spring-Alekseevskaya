package main.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.CommentRepository;
import main.domain.Book;
import main.domain.Comment;
@Service
public class CommentServices {

	private final CommentRepository commentRepository;
	
	@Autowired
	public CommentServices(CommentRepository commentRepository)
	{
		this.commentRepository = commentRepository;
	}
	
	public CommentRepository getRepository() {
		return commentRepository;
	}
	public boolean addAnonimousComment(String text, Book b) {
		if (b != null) {
			Comment c = new Comment(text, b);
			commentRepository.save(c);
			return true;
		}
		return false;
	}
	
	public String printAllCommentsByBook(Book b) throws Exception {
		if (b != null) {
			List<Comment> comments = commentRepository.findByBook(b);
			StringBuilder sb = new StringBuilder();
			int i = 1;
			for (Comment comment : comments) {
				sb.append(i + ". ");
				sb.append(">> " + comment.toString() + "\n");
				i++;
			}
			return sb.toString();
		} else
			throw new Exception("Book is not found");
	}
	
	public boolean addComment(Book b) {
		if (b != null) {
			Scanner sc = new Scanner(System.in);

			System.out.println("Enter your name");
			String nickName = sc.nextLine().trim();

			System.out.println("Enter your comment");
			String commentText = sc.nextLine().trim();

			Comment c = new Comment(nickName, commentText, b);
			commentRepository.save(c);
			return true;
		}
		return false;

	}
}
