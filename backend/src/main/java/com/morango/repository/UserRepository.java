package com.morango.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morango.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}