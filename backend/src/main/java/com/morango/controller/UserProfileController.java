package com.morango.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.morango.model.entities.Posts;
import com.morango.model.entities.User;
import com.morango.repository.PostsRepository;
import com.morango.repository.UserRepository;

@Controller
public class UserProfileController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;
    
    @GetMapping("/user/profile")
    public String userProfile(@RequestParam("username") String username, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(principal.getName());
        User profileUser = userRepository.findByUsername(username);
        List<Posts> userPosts = postsRepository.findByAuthor(profileUser);
        model.addAttribute("user", user);
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("posts", userPosts);
        return "UserProfile";
    }
    
}

