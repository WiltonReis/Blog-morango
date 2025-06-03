package com.morango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.morango.model.dto.UserAuthDTO;
import com.morango.model.entities.User;
import com.morango.repository.UserRepository;
import com.morango.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;
    
    @GetMapping("/login")
    public String login() {
        return "Login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute UserAuthDTO userAuthDTO, HttpServletResponse response) {
        userLogin(userAuthDTO, response);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null); // Zera o token
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Define como seguro
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira imediatamente

        response.addCookie(cookie);

        SecurityContextHolder.clearContext();

        return "redirect:/";
}

    @GetMapping("/register")
    public String register() {
        return "Register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute UserAuthDTO userAuthDTO, HttpServletResponse response) {
        if(repository.findByUsername(userAuthDTO.getUsername()) != null) {
            System.out.println("Usuário já existe");
            return "redirect:/register";
        } else {
            User entity = new User(userAuthDTO.getUsername(), passwordEncoder.encode(userAuthDTO.getPassword()));
            repository.save(entity);
            userLogin(userAuthDTO, response); // Login automático após registro
            return "redirect:/";
        }
    }

    private void userLogin(UserAuthDTO userAuthDTO, HttpServletResponse response){
        var token = authService.authenticate(userAuthDTO);
        
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(2 *60 * 60); // 2 horas

        response.addCookie(cookie);
    }

    

}
