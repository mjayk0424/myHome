package com.mjay.myHome.board.repository;


import com.mjay.myHome.mjay.dto.PageRequestDTO;
import com.mjay.myHome.mjay.entity.Board;
import com.mjay.myHome.mjay.entity.Member;
import com.mjay.myHome.mjay.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board); // CrudRepository<T, ID>.save(S entity)

        });

    }


    @Transactional //해당 메서드를 하나의 트랜잭션으로 처리
    @Test
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호

        Board board = result.get();

        Board result2 = boardRepository.getOne(100L);

        System.out.println("======================== testRead1 start ======================================");
        System.out.println(board);
        System.out.println(board.getWriter());

//        System.out.println(result);
//        System.out.println(result.get().toString());
//        System.out.println(result.get().getWriter());
//        System.out.println(result2);
//        System.out.println(result2.toString());
//        System.out.println(result2.getWriter());
        System.out.println("======================== testRead1 end ======================================");


    }

    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------------");
        System.out.println(Arrays.toString(arr));

    }

    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }


    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3() {

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
    }

//    @Test
//    public void testSearch1() {
//
//        boardRepository.search1();
//
//    }
//
//    @Test
//    public void testSearchPage() {
//
//        Pageable pageable =
//                PageRequest.of(0,10,
//                        Sort.by("bno").descending()
//                                .and(Sort.by("title").ascending()));
//
//        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
//
//    }
//
//    @Test
//    public void testSearch(){
//
//        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//        pageRequestDTO.setPage(1);
//        pageRequestDTO.setSize(10);
//        pageRequestDTO.setType("twc");
//        pageRequestDTO.setKeyword("3");
//
//        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());
//
//        Page<Object[]> result = boardRepository.searchList( pageRequestDTO );
//
//        System.out.println(result);
//
//        result.get().forEach(row -> {
//
//            Object[] arr = (Object[])row;
//
//            System.out.println(Arrays.toString(arr));
//        });
//
//    }


}
