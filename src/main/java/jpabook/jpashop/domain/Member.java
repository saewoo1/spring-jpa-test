package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

  @Id @GeneratedValue // Generated ~ -> sequence 값으로 쓰기 가능
  @Column(name = "member_id")
  private Long id;

  private String name;

  @Embedded // 내장타입 선언
  private Address address;

  /*
  * mappedby -> 난  ordertable에 있는 member 필드에 의해 맵드 된거당 읽기 전용, 맵드 거울
  * 주인이 아니셈, 나 바꿔봤자 FK 안바뀝니다
  * */
  @OneToMany(mappedBy = "member") // 멤버 - 오더는 일대 다 관계, 오더 테이블의 멤버!! 양방향 맵핑
  private List<Order> orders = new ArrayList<>(); // 초기화 고민할 필요 없다 이런 식으로 초기화는..

}
