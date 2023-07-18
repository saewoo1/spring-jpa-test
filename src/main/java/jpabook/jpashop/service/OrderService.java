package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**주문*/
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem =  OrderItem.createOrderItem(item, item.getPrice(), count); // 이런 식으로 생성메서드를 사용해 orderItem을 만들도록 유도.

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); // 얘만 있어도 orderItem, delivery도 persist가 된다!

        return order.getId();
    }
    /**
    * 원래라면, delivery, orderItem을 각각 따로 persist 해줘야하는데 orderRepository.save 한번만 했음.
    * 이건 Order 도메인의 orderItem 선언 시 cascade 어노테이션을 걸어줘서 order를 persist 하면 ->
    * 들어있는 orderItems들도 강제로 persist를 수행한다.
    *
    * 이런 cascade는 다른 데에서 참조되지 않고 오로지 하나의 private owner에서만 참조되는 경우에서 사용하도록 하자
    */


    //취소
    @Transactional
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
