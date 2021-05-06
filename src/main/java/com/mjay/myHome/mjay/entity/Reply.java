package com.mjay.myHome.mjay.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board") //ToString 메소드 자동 생성 exclude의 값 제외
public class Reply extends BaseEntity{

    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임(id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다), 시퀀스 개념
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne // Reply N : 1 Board join
    private Board board;  //연관관계(참조) 지정 , board_bno FK 컬럼이 생성 됨

}
