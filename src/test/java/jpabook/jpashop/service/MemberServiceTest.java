package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 실행 시 spring과 함께 돌릴ㄹ래
@SpringBootTest // Springboot 띄운 사 ㅇ태로 테스트 돌릴래. Atuowired 다 터짐 없으면
@Transactional // 테스트에선 db 변경 후에 다시 롤백 시켜줌 커밋 안함
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
        /*
        * 이런 식으로 검증이 가능한 이유는
        * join으로 넣은 파라미터 값이랑, Member랑 동일한지 검수하는 과정임.
        * jpa에서 같은 transctional안에서 같은 Entity id값(PK)이 같으면 같은 영속성 컨텐츠로 관리가 되기 때문.
        * */
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return ;
//        } expected 어노테이션 추가로 줄일 수 있다.
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야합니다.");

    }

}