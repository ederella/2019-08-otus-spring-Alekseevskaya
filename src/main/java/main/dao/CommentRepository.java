package main.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import main.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

}
