package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor // 필드의 모든 생성자를 만들어준다.
@RequiredArgsConstructor // final이 있는 Field만 가지고 생성자를 만들어준다 롬복
public class MemberService {

    //근데 이러면 필드고, Private라 못바꾸는뎅..!!!
    //@Autowired // service 로직에서 memberRepository 사용할껀데 Spring아 올려주라
    //final? 컴파일 시점에서 체크를 할 수 있음
    private final MemberRepository memberRepository;

//    @Autowired // 생략 가능, 생성자 하나만 있으면 Spring이 자동으로 올려준다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    } // 생성자 인젝션.. 필드로 바로 목업을 주입하기 까다로워서 이런 식으로
    // 테스트 혹은 컴파일 중에 초기화 안됐는지 꼭 보셈

    //회원 가입
    @Transactional // db변경은 꼭 트랜젝션 안에서 하자. 클래스 수준에서 하면 Public 애들은 자동으로 걸려 spring꺼 스셈
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복회원 비즈니스 로직
        memberRepository.save(member); // save 하는순간 Persistence -> id 들어감
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } // 이것보다 멤버 수를 세는게 좀 더 최적화에 좋은..
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
