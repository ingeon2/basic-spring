package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        //MemberService memberService = new MemberServiceImpl(); 이게 기존의 방식.

        Member member = new Member(1L, "memeberA", Grade.VIP);
        memberService.join(member);
        
        Member findMemeber = memberService.findMember(1L);
        
        //여기 아래는 서비스가 제대로 만들어졌는지 확인하는 로직
        System.out.println("new member = " + member.getName());
        System.out.println("findMemeber = " + findMemeber.getName());
    }
}
