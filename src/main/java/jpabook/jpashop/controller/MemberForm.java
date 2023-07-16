package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/*
* dto를 쓰는 이유
* controller에서 딱 필요한 부분, 노출되어도 되는 부분만 dto로 생성해서
* view와 맞는 entity를 이용하는 것이 좋다.
* */
@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다람쥐 타임리프 지린다")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
