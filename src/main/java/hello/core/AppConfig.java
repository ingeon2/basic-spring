package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceLmpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    //각각의 클래스에서 만든 생성자를 여기서 매서드를 만드는데 사용함


    //리팩터링함 (memberRepository 로 꺼내서 더 가독성 좋게), 리팩터링을 통해 한눈에 다이어그램처럼 들어올 수 있게
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    //appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl 을
    //생성하면서 생성자로 전달한다.
    //클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
    //DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.


    //리팩터링함 (discountPolicy 로 꺼내서 더 가독성 좋게), 리팩터링을 통해 한눈에 다이어그램처럼 들어올 수 있게
    @Bean
    public OrderService orderService(){
        return new OrderServiceLmpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
        //return new FixDiscountPolicy();

        //FixDiscountPolicy RateDiscountPolicy 로 변경해도 구성 영역(AppConfig)만 영향을 받고,
        //사용 영역은 전혀 영향을 받지 않는다. (DI 이전에는 Impl 들어가서 구체화된 얘들 바꿔야했잖아)
    }
}

//AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 각각 필드 수(필요한 객체 수)에 맞춰 생성한다.
//MemberServiceImpl
//MemoryMemberRepository
//OrderServiceImpl
//FixDiscountPolicy

//AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다.
//MemberServiceImpl MemoryMemberRepository
//OrderServiceImpl MemoryMemberRepository , FixDiscountPolicy

//즉, 구현부에서는 비즈니스 로직만 구현하면 되는것이지, 어떤 배우(고정금액, 일정비율, 메모리 등등)를 사용할지는 여기 AppConfig에서 설정하면 되는것이다.



//이렇게 만든 DI 의 장점은..?
/*
DI 장점
그렇다면, DI, 의존 관계를 분리하여, 주입을 받는 방법의 코드 구현은 어떠한 장점이 있을까요?

        1. 의존성이 줄어든다.

        앞서 설명했듯이, 의존한다는 것은 그 의존대상의 변화에 취약하다는 것이다.(대상이 변화하였을 때, 이에 맞게 수정해야함)
        DI로 구현하게 되었을 때, 주입받는 대상이 변하더라도 그 구현 자체를 수정할 일이 없거나 줄어들게됨.

        2. 재사용성이 높은 코드가 된다.

        인터페이스의 하위 클래스만 만들어주면 언제든지 재사용 가능하다.

        3. 테스트하기 좋은 코드가 된다.


        4. 가독성이 높아진다.

        기능들은 별도로 OrderServiceLmpl 클래스나 MemberServiceImpl 클래스에 분리하게 되어 자연스레 가동성이 높아진다.*/
