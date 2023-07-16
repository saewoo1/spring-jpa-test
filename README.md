## 정리

1. 즉시로딩(EAGER), 지연로딩(LAZY)
   1. 즉시로딩 : 멤버 조회 시 연관된 오더들을 한번에 조회. 다 끌고와서 어떤 SQL이 실행되는지 예측이 어렵다.
   2. 지연로딩 : 기본적으로 LAZY로 세팅하길 권장. 추후에 필요한 Entity만 fetch join, 엔티티 그래프로 가져오면 됨.
   3. 추가적으로 @XToOne관계(하나를 찌르는 관계)는 기본적으로 EAGER.. LAZY로 꼭 직접 지연로딩을 설정해주자.
2. 컬렉션은 필드에서 바로 초기화하는게 좋다.

3. 테이블명 입맛대로 변경 원하면 hibernate.naming.physical-strategy 변경 타주면 됨.(물리명, 논리는 Implicit)
4. cascade = CascadeType.all
   1. Order Entity에 적용함.
   2. Orderitems에 데이터를 저장하면 Order에도 저장이 된다.
      3. ex)
      4. persist(orderItemA), b, c 후에 -> persiste(order) 이렇게해야되는데
      5. 저 명령어가 있으면 persist(order)만 있으면 OrderitemAbc도 자동으로 저장, Order에 들어온 데이터들을 전파해주는 방식

5. 계층형 구조 사용
   1. controller, web : 웹 계층
   2. service : 비즈니스 로직, 트랜잭션 처리
   3. repository : JPA를 직접 사용하는 계층, 엔티티 매니저 사용
   4. domain : 엔티티가 모여있는 계층, 모든 계층에서 사용


6. createMemberForm.html
   1.  11줄 th:object=${memberForm}
      1. 이 form이라는 태그 안에서는 MemberForm 객체를 계속 사용한다. method=post
   2. 14줄 *{name}
      1. getter, setter를 통한 접근법. memberForm의 name field에 접근한다.
      2. th:field="~~" th:field 요렇게 작성하면 id, name 똑같이 넣어줌 ex) city -> id= "city", name="city"
