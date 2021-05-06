package com.mjay.myHome.mjay.controller;

import com.mjay.myHome.mjay.dto.ReplyDTO;
import com.mjay.myHome.mjay.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mjay/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE) //요청을 JSON TYPE의 데이터만 담고있는 요청을 처리하겠다는 의미가 된다.
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno ){
        //화면단에서 버튼 클릭 시 호출
        log.info("bno: " + bno);

        ResponseEntity<List<ReplyDTO>> result = new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK);

        return result;

    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        //RequestBody : JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑해 주는 역할
        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        log.info("RNO:" + rno );

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    @PutMapping("/modify/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }





}
