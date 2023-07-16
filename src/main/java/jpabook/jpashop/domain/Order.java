package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = LAZY) // 오더 - 멤버는 다대 일 관계, 주인은 이렇게 두면 됨
  @JoinColumn(name = "member_id") // FK가 member_id가 됐다.
  private Member member;
  //주로 FK가 가까운 곳이 연관관계 주인으로.
  // 양방향이면 Onetomany-manytoone 걸어줘야 둘중 하나 업데이트 되면 나머지도 업데이트 되는데...흠다
  //이 케이스에서는 이 member를 바꾸면 fk값도 변경됨

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  //order를 저장할 때 delivery 객체만 있으면 여기에다가도 persist 해준다.
  @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate; // 주문 시간 알아서 해줌.

  @Enumerated(EnumType.STRING)
  private OrderStatus status; // 주문상태 [Orer, cancel] Enum

//
//  public static void main(String[] args) {
//    Member member1 = new Member();
//    Order order = new Order();
//
//    member1.getOrders().add(order);
//    order.setMember(member1);
//    //원래는 이렇게 해줘야됨
//  }

  //==양방향 연관관계 편의 메서드를 통해 위 코드를 한방에==//
  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }
  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  } // 양쪽이 서로 영향을 받아야하는 코드일 때, 이런 식으로 한방에

  //== 생성 메서드==//
  //외부에서 setter  사용을 막기 위해 생성할 때부터 미리 세팅을 해줌..주문할때 뭔가 변경점이 있으면 여기를 고치면 됨
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);
    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  //==비즈니스 로직==//
  //주문 취소
  public void cancel() {
    if (delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능합니다.");
    }

    this.setStatus(OrderStatus.CANCEL);
    for (OrderItem orderItem : this.orderItems) {
      orderItem.cancel();
    }
  }

  //==조회 로직==//
  public int getTotalPrice() {
    // int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    int totalPrice = 0;
    for (OrderItem orderItem : orderItems) {
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;
  }
}
