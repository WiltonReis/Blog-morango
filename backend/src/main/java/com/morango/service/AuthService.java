package com.morango.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.morango.model.dto.UserAuthDTO;
import com.morango.model.entities.User;
import com.morango.repository.UserRepository;
import com.morango.security.TokenService;

@Service
public class AuthService {
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public String authenticate(UserAuthDTO userAuthDTO) {
        var usernamePass = new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        var  authentication = authenticationManager.authenticate(usernamePass);
        User user = userRepository.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        
        return tokenService.generateToken(user);
    }
}
