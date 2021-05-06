package com.mjay.myHome.mjay.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mjay/member")
@Log4j2
public class MemberController {

    @GetMapping("/join")
    public String join(){

        log.info("....................... join form ");

        return "/mjay/member/join";
    }
    @PostMapping("/join")
    public void ajaxJoin(){

        log.info("....................... join");

    }
}
