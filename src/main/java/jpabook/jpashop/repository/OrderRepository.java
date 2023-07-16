package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        //동적 쿼리 처리방법
        return em.createQuery("select o from Order o join o.member m" +
                        " where o.status = :status " + // 파라미터 바인딩 o.status는 status 값으로 세팅
                        " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(100) // 개수 제한 걸기
                .getResultList();
    }

    /*
     * 1. Order 조회
     * 2. Order와 Order와 연관된Member를 join
     * 3. where 전까지 상황이엇음
     * jpql을 사용해서 개억지 동적쿼리를 짜는건 비추
     * */
}
