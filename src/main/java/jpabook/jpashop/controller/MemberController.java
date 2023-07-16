package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
    * model?
    * attribute를 통해서 데이터 객체를 넘긴다.
    * 현재 시점에서는 dto역할을 하는 memberForm 빈 껍데기를 들고 createMemberForm을 탐
    * 이러면 화면에서 MemberFrom이라는 객체에 접근할 수 있게된다.
    *
    * GetMapping -> GET으로 method 요청이 들어오면 함수를 수행하고, members/createMemberForm으로 렌더링, 열어보기
    *
    * PostMapping -> POST 요청이 오면 실제 데이터 등록
    * @Valid -> 알아서 밸리데이션 해주랑
    * @BindingResult -> 오류 발생 시 이 오류 담겨서 실행
    * */
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member); // 저장
        return "redirect:/";
    }
}
