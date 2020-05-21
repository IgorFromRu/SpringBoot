package com.mywebproject.config;


import com.mywebproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()                                            //включаем авторизацию
                    .antMatchers("/", "/registration", "/static/**").permitAll()  //разрешение полного доступа на донной странице
                    .anyRequest().authenticated()                               // для остальных заросов требуем авторизацию
                .and()
                    .formLogin()                                                // форма логина
                    .loginPage("/login")                                        // указываем что страница логина находится на данном адресе
                    .permitAll()                                                // разрешаем этим пользоваться всем
                .and()
                    .logout()                                                   // включаем выход из логина
                    .permitAll();                                               // разрешаем этим пользоваться всем
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //берем пользователя из базы данных
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());                 // шифрует пароли
    }

}
