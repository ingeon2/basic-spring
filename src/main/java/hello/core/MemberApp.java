package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        여기가 스프링 이전 2번
//        MemberService memberService = new MemberServiceImpl(); 
//        이게 기존의 DI 이전 1번 방식.

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
         //쉽게말해서 applicationContext 에 AppConfig 할당, in 스프링 컨테이너
        //memberService 에 applicationContext를 사용한 매서드(getBean)로 MemberService 할당. in 스프링 컨테이너
        //("memberService" 이거는 이 이름의 매서드를 찾을거라는 뜻. 그러면 혼자 주석 없이  OrderApp 바꿔보기.)

        Member member = new Member(1L, "memeberA", Grade.VIP);
        memberService.join(member);
        
        Member findMemeber = memberService.findMember(1L);
        
        //여기 아래는 서비스가 제대로 만들어졌는지 확인하는 로직
        System.out.println("new member = " + member.getName());
        System.out.println("findMemeber = " + findMemeber.getName());
    }
}
