package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장타입을 포함했다는 어노테이션
@Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;

  protected Address() { // jpa스펙상 만든거
  }
  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode =zipcode;
  }
}
//mysql이랑 연결한 후에 워크밴치에서 한눈에 보면 더 좋을 것 같다..