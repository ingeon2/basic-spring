package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("과연 스프링은 AppConfig에서 new로 여러번 생성되는 객체들도 싱글톤으로 관리할것인지..?")
    void configuationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository1= memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        MemberRepository memberRepository3 = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println("memberService → memberServiceRepository = " + memberRepository1);
        System.out.println("orderService → memberServiceRepository = " + memberRepository2);
        System.out.println("memberServiceRepository = " + memberRepository3);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository3);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository3);

        //확인해보면 memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다.
    }

    @Test
    @DisplayName("스프링은 완벽한 singleton을 구사한다 by 바이트코드 조작으로 그냥 다른 클래스를 만들어버림")
    //바이트코드 조작은 @Configuration 때문에 이루어짐
    //즉 @Bean만 작성하면 싱글톤 보장되지 않는다!
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        //예상은 bean = class hello.core.AppConfig인데 이렇게 안나옴. 그 이유는..?
        //그런데 예상과는 다르게 클래스 명에 xxxCGLIB가 붙으면서 상당히 복잡해진 것을 볼 수 있다.
        //이것은 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig
        //클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!
    }
}
