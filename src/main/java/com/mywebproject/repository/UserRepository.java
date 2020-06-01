package com.mywebproject.repository;

import com.mywebproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); //метод который возвращяет имя пользователя

    User findByActivationCode(String code);
}
