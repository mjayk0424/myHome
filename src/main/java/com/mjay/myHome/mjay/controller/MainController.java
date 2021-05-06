package com.mjay.myHome.mjay.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/","/mjay"})
@Log4j2
public class MainController {

    @GetMapping({"/","/home"})
    public String index(HttpServletRequest request, Model model ){
        log.info(".......................................home ");

        return "/mjay/home";
    }
    @GetMapping("/test/test")
    public String test(){
        log.info(".......................................test ");
        return "/mjay/test/test";
    }
}
