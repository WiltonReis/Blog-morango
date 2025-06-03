package com.morango.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morango.model.entities.Posts;
import com.morango.model.entities.User;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    List<Posts> findByAuthor(User user);
}
