package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    //MemberService memberService = new MemberServiceImpl();
    //OrderService orderService = new OrderServiceLmpl();

    @Test
    void createOrder(){
        Long memberId = 1L; //그냥 변수
        Member member = new Member(memberId, "memberA", Grade.VIP); //바로위의 변수로 member 객체 생성
        memberService.join(member); //member객체 생성 후 리파지토리에 넣고

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000); //검증
    }


}
