package com.morango.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.morango.model.entities.Posts;
import com.morango.repository.PostsRepository;
import com.morango.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    
@Autowired
private UserRepository userRepository;

@Autowired
private PostsRepository postsRepository;

@GetMapping("/")
    public String home(Model model, Principal principal, @RequestParam (value = "keyword", required = false) String keyword) {
        if (principal != null) {
            model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        }else {
            model.addAttribute("user", null);
        }
        List<Posts> posts;
        if(keyword != null && !keyword.isEmpty()) {
            posts = postsRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
        } else {
            posts = postsRepository.findAll();
        }
        model.addAttribute("posts", posts);

        return "Home";
    }

    @GetMapping("/posts/create")
    public String CreateNewPost(Model model) {
        model.addAttribute("post", new Posts());
        return "CreateNewPost";
    }

    @PostMapping("/posts")   
    public String savePost(@ModelAttribute Posts post, Principal principal) {
    if (principal != null) {
        var user = userRepository.findByUsername(principal.getName());
        post.setUser(user);
        postsRepository.save(post);
    }
    return "redirect:/";
}
    
}
