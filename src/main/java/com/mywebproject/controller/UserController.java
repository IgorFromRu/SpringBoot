package com.mywebproject.controller;

import com.mywebproject.domain.Role;
import com.mywebproject.domain.User;
import com.mywebproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PreAuthorize("hasAuthority('ADMIN')")          //даем доступ только Админу
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());        // выводим список пользователей

        return "userList";
    }
    @PreAuthorize("hasAuthority('ADMIN')")          //даем доступ только Админу
    @GetMapping("{user}")                                               //метод изменеия данных пользователя
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PreAuthorize("hasAuthority('ADMIN')")          //даем доступ только Админу
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.saveUser(user, username, form);

        return "redirect:/user";
    }
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }
    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ){
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
}
