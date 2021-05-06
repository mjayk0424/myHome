package com.mjay.myHome.mjay.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //ToString 메소드 자동 생성 exclude의 값 제외
public class Board extends BaseEntity{

    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임(id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다), 시퀀스 개념
    private Long bno;

    private String title;

    private String content;

    // Member 1 : N Board join
    @ManyToOne (fetch = FetchType.LAZY) //LAZY 불필요한 테이블은 조인하지 않는다. 반대로 EAGER는 모든 테이블은 조인한다.(성능저하)
    private Member writer; //연관관계(참조) 지정 , writer_email FK 컬럼 생성 됨

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
