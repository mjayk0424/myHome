package com.mjay.myHome.mjay.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mjay/login")
@Log4j2
public class LoginController {

    @GetMapping("/login")
    public String login(){
        log.info("login.......................................");

        return "/mjay/login/login";

    }
    @GetMapping("/logout")
    public String logout(){
        log.info("logout.......................................");
        return "/mjay/login/logout";
    }
}
