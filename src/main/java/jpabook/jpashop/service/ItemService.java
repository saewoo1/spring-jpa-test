package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book param) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        /*
        변경감지
        * 더이상 관리받지 못하는 준영속성 entity Book을 수정하는 방법
        * 영속성 Entity인 item을 기준으로 findItem을 잡고, param의 price로 수정을 해주는 방식
        * 이러면 JPA는 영속성 엔티티인 item을 바라보고 있었으니, 자동으로 db에 수정이 가능해진다.
        * 다시 save할 필요도 없음 Transactional이 얘를 보고 있으니까!

        원하는 필드만 세팅이 가능하다.
        * */
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
