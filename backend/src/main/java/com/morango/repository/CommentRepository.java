package com.morango.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morango.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}
