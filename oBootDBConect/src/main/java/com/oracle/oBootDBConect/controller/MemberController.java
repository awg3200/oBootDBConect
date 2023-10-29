package com.oracle.oBootDBConect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootDBConect.domain.Member1;
import com.oracle.oBootDBConect.service.MemberService;
//내장 톰캣이라 포트번호 적지 않으면 충돌일어나므로 application.properties에 적어줘야 함
@Controller
public class MemberController {
	private final MemberService memberService;
//	@Autowired는 스프링 프레임워크에서 사용되는 어노테이션으로, 주로 의존성 주입(Dependency Injection)을 수행하기 위해 사용
//스프링은 빈(bean)을 관리하고, 이러한 빈들 간에 서로 의존하는 경우, @Autowired 어노테이션을 사용하여 해당 의존성을 자동으로 주입할 수 있음
//  생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌
  //    객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 함
  //    이전 에서는 개발자가 직접 주입했고(지금까지 예제에서 한 방법), 여기서는 @Autowired에 의해 스프링이 주입

  //    스프링 빈을 등록하는 2가지 방법
  //    컴포넌트 스캔과 자동 의존관계 설정
  //    자바 코드로 직접 스프링 빈 등록하기

  //    @Component 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록.
  //    @Controller
  //    @Service
  //    @Repository
	@Autowired
	public MemberController(MemberService memberService) {
//		@Autowired로 di주입하려면 memberService가 인스턴스화되어야 함 따라서 MemberService에 @Service하면 1011 9:17
//		스프링 버전 올라가면서 만약 서비스가 하나라면 @Autowired 안써도 알아서 di 주입해줌 하지만 익숙해지기 전까진 써주는 게 좋음
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/members/memberForm")
	public String createMemberForm() {
		System.out.println("MemberController /members/memberForm start...");
		return "members/createMemberForm";
	}
	
	@PostMapping(value = "/members/newSave")
	public String memberSave(Member1 member	) {
		System.out.println("MemberController memberSave start...");
		memberService.memberSave(member);
		return "redirect:/";
	}
	
	@GetMapping(value = "/members/memberList")
	public String memberLists(Model model) {
		System.out.println("MemberController memberLists start...");
		List<Member1> memberList = memberService.findMembers();
		model.addAttribute("members", memberList);
		return "members/memberList";
	}
	
}
