package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceLmpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        //AppConfig appConfig = new AppConfig(); 2번
        //MemberService memberService = appConfig.memberService(); 2번
        //OrderService orderService = appConfig.orderService(); 2번

        //MemberService memberService = new MemberServiceImpl(); 1번
        //OrderService orderService = new OrderServiceLmpl(); 1번

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L; //그냥 변수
        Member member = new Member(memberId, "memberA", Grade.VIP); //바로위의 변수로 member 객체 생성
        memberService.join(member); //member객체 생성 후 리파지토리에 넣고

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}
