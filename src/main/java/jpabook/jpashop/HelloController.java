package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("hello")
  public String hello(Model model) { // model -> data 실어서 view로 넘길수잇음
    model.addAttribute("data", "hello!!");
    return "hello"; // 화면 이름 hello.html이 자동으로 붙음 어케 찾았누? springboot->thymeleaf
  }
}
