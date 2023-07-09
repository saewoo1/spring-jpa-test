package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // component scan -> bean 관리 자동
@RequiredArgsConstructor // spring jpa에서 제공함. em도 Autowired 쓸 수 있게..!
public class MemberRepository {

//    @PersistenceContext // em을 만들어서 spring에 injection! Autowired 사용으로 생략 가능.
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // member를 저장하는 로직 transaction이 커밋되는 시점에 반영된다.
    }

    public Member findOne(Long id) { // 단권조회 jpa의 ->find(type, PK)
        /*
        *   Member member = em.find(Member.class, id);
        *   return member;
        *   이걸 한줄로
        * */
        return em.find(Member.class, id); // id값을 기반으로 member를 찾아 반환
    }

    /*
    * jpql은 entity를 대상으로 쿼리를 돈다!
    * */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); //create~ : jpql 작성, 반환 타입 한줄 변환은 커맨드 알트 앤
    }

    public List<Member> findByName(String name) { // 회원의 name으로 조회하기
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
