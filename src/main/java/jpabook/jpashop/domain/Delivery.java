package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

  @Id
  @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery",
    fetch = FetchType.LAZY)
  private Order order;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING) // 숫자(ordinary)하면 안됨. 중간에 새로 끼면 뒤에꺼 다 밀린다..
  private DeliveryStatus status; // ready, comp
}
