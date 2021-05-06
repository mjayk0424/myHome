package com.mjay.myHome.mjay.controller;

import com.mjay.myHome.mjay.dto.BoardDTO;
import com.mjay.myHome.mjay.dto.PageRequestDTO;
import com.mjay.myHome.mjay.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mjay/Tmap/")
@Log4j2
@RequiredArgsConstructor
public class TMapController {

    @GetMapping("/view")
    public String list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list............." + pageRequestDTO);


        return "/mjay/Tmap/view";
    }
}
