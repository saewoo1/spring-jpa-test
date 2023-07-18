package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item); // merge -> update비섯함
            //Item book = em.merge(item); // 이런식으로 하면 item 그대로 준영속성, Item만 persist 차라리 이게 나은듯;
            /*
             * em의 모든 데이터를 넘어온 param(item)으로 바꿔침.
             * 모든 필드를 갖다박음
             * 준영속성 -> 영속성 변경
             * 작동 방식은 변경감지와 비슷하지만 좀 더위험하지 않나여..
             * 병합 시 모든 필드를 교체하니까, 뭐 하나라도 없으면 NUll로 바꿔버린다 원하는 필드만 선택 불가능.
             * */
        }
    }

    public Item findOne(Long id) {
        return  em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return  em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
