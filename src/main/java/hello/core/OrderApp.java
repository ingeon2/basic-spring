package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceLmpl;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();
        //MemberService memberService = new MemberServiceImpl(); 이전의 방법
        //OrderService orderService = new OrderServiceLmpl(); 이전의 방법

        Long memberId = 1L; //그냥 변수
        Member member = new Member(memberId, "memberA", Grade.VIP); //바로위의 변수로 member 객체 생성
        memberService.join(member); //member객체 생성 후 리파지토리에 넣고

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}
