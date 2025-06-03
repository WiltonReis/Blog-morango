package com.morango.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.morango.model.entities.Comment;
import com.morango.repository.CommentRepository;
import com.morango.repository.PostsRepository;
import com.morango.repository.UserRepository;

@Controller
public class PostController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("post", postsRepository.findById(id).orElse(null));
        model.addAttribute("comment", new Comment());
        return "ViewPost";
    }

    @PostMapping("/posts/{id}/comment")
    public String commentOnPost(@PathVariable Long id, Model model, Principal principal, Comment comment) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("post", postsRepository.findById(id).orElse(null));
        comment.setPost(postsRepository.findById(id).orElse(null));
        comment.setAuthor(userRepository.findByUsername(principal.getName()));
        commentRepository.save(comment);
        return "ViewPost";
    }

}