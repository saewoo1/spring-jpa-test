package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
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
}
