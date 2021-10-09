package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// Spring이 처음 생길 때, Spring container라는 큰 통이 생기는데
// 이렇게 controller annotation을 적어두면 MemberController라는 객체를 생성해서
// Spring에 넣어두게 된다. 그리고 Spring에서 관리를 하게 된다.
// == Spring Container에서 Spring Bean이 관리된다.
@Controller
public class MemberController {
    /* 아래와 같이 생성할 수 있지만
     다른 controller들에서도 MemberService를 생성해서 쓸 수 있는데
     이건 여러개 생성해서 쓸 필요 없고 하나만 생성해서 공용으로 쓰면 더 좋음
     따라서 Spring Container에 등록하고 쓰면 하나만 쓸 수 있음 + 부가적 효과(뒤에서 설명) */

    // private final MemberService memberService = new MemberService();

    /*
    1. 필드 주입
    @Autowired private MemberService memberService;
    */

    /*
    2. setter 주입
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
     */

    /*
    3. 생성자 주입(권장)
     */
    private final MemberService memberService;


    // 이렇게 Autowired라고 annotation이 생성자에 적혀져 있으면
    // 아래의 생성자를 실행할 때 Spring container에서 MemberService를 해당 생성자의 인자에 넣어준다.
    // 이렇게 주입하는 것을 DI(Dependency Injection)이라고 함
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
