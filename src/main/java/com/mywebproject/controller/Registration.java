package com.mywebproject.controller;

import com.mywebproject.domain.Role;
import com.mywebproject.domain.User;
import com.mywebproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class Registration {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("message", "");
        return ("/registration");
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String ,Object> model){
        User userFromDB = userRepo.findByUsername(user.getUsername());      // берет имя пользователя из базы

        if (userFromDB != null){                                             //выдает сообщение если пользователь существует
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);                               //задаем пользователю активность
        user.setRoles(Collections.singleton(Role.USER));    //задаем роль
        userRepo.save(user);                                //сохраням пользователя
        return "redirect:/login";                           // возвращяет на страницу логина, если регистрация успешна
    }
}
