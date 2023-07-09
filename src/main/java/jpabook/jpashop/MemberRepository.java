//package jpabook.jpashop;
//
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Repository // component scan의 대상이 됨 -> 자동으로 Spring Bean에 등록
//public class MemberRepository { // entity 찾아주는 애
//
//  @PersistenceContext // jpa 사용을 위해 entity 쓸라고, springboot가 얘 보면 entitymanager 주입해줌
//  private EntityManager em;
//
//  public Long save(Member member) {// 커맨드 쉬프트 티 -> 테스트 작성
//    em.persist(member);
//    return member.getId(); // 커맨드와 쿼리 분리. 저장을 하면 하나의 커맨드로 작동 했으니 최대한 리턴값 없이. 근디 아이디정도는 있으면 다음에 조회 가능함
//  }
//
//  public Member find(Long id) {
//    return em.find(Member.class, id); // id갖구 조회해줭
//  }
//}
