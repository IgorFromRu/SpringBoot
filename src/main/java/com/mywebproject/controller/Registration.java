package com.mywebproject.controller;

import com.mywebproject.domain.User;
import com.mywebproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class Registration {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("message", "");
        return ("/registration");
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String ,Object> model){

        if (!userService.addUser(user)){                                             //выдает сообщение если пользователь существует
            model.put("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";                                                    // возвращяет на страницу логина, если регистрация успешна
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if (isActivated){
            model.addAttribute("message", "User Successfully activated");
        }else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
