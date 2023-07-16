package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 하나의 테이블(item)에 모두 넣을거임
@DiscriminatorColumn(name = "dtype") // dis~value 안에 들어 있는 타입 모두 구분해줌 book?
@Getter @Setter
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name =  "item_id")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  //비즈니스 로직  -> 데이터를 가지고 있는 쪽에서 비즈니스 메서드를 생성하도록 하자.

  /*
  *   stock 증가
  * */
  public void addStockQuantity(int quantity) {
    this.stockQuantity += quantity;
  }

  public void removeStockQuantity(int quantity) {

    int restStock = this.stockQuantity - quantity;

    if (restStock < 0) {
      throw new NotEnoughStockException("need more stock. can't be less than zero.");
    }
    this.stockQuantity = restStock;
  }
  // 지금 데이터를 지니고 있는 쪽에서 비즈니스 로직을 만들어주는게 좋다. 외부에서 Setter 사용을 지양하자.
}
