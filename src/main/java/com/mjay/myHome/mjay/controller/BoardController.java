package com.mjay.myHome.mjay.controller;


import com.mjay.myHome.mjay.dto.BoardDTO;
import com.mjay.myHome.mjay.dto.PageRequestDTO;
import com.mjay.myHome.mjay.dto.PageResultDTO;
import com.mjay.myHome.mjay.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mjay/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list............." + pageRequestDTO);

        PageResultDTO<BoardDTO, Object[]> boardServiceList =  boardService.getList(pageRequestDTO);
        model.addAttribute("boardServiceList", boardServiceList);

//        return "/mjay/board/list";
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/register")
    public void register(){
        log.info("regiser get...");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        log.info("boardDTO..." + boardDTO);
        //새로 추가된 엔티티의 번호
        Long bno = boardService.register(boardDTO);

        log.info("BNO: " + bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/mjay/board/list";
    }

    @GetMapping({"/read", "/modify" ,"/read2"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){

        log.info("bno: " + bno);

        BoardDTO boardDTO = boardService.get(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);

    }


    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){


        log.info("bno: " + bno);

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/mjay/board/list";

    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        log.info("borad post modify.........................................");
        log.info("dto: " + dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/mjay/board/read";

    }


}
