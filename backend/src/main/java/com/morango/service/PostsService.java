package com.morango.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morango.model.entities.Posts;
import com.morango.repository.PostsRepository;

@Service
public class PostsService {
    
    @Autowired
    private PostsRepository postsRepository;
    
    public List<Posts> findAll() {
        return postsRepository.findAll();
    }
    
    public Posts findById(Long id) {
        return postsRepository.findById(id).orElse(null);
    }
    
    public Posts save(Posts posts) {
        return postsRepository.save(posts);
    }
    
    public void delete(Posts posts) {
        postsRepository.delete(posts);
    }
}