package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 DI 컨테이너. 즉 싱글톤 적용 X")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();

        // 조회 : 호출할 때 마다 객체 생성
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        //서로 다르다는 것 보여주기
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //실제로 다른지 매서드로 확인. (isNotSameAs 라서 달라야 통과)
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("자바에서 순수하게 싱글톤으로 만든 객체 사용")
    void singletonServiceTest(){
        //private로 new 키워드 막아서 오직 이렇게 사용할 수 밖에 없음
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //서로 같다는 것 보여주기
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        //실제로 같은지 매서드로 확인. (isSameAs 라서 달라야 통과)
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤!")
    void springContainer(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 조회 : 호출할 때 마다 객체 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        
        //서로 같다는 것 보여주기
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //실제로 같은지 매서드로 확인. (isSameAs 라서 같아야 통과)
        Assertions.assertThat(memberService1).isSameAs(memberService2);

        //스프링 컨테이너 덕분에 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로 재사용할 수 있다.
    }



}
//싱글톤을 사용하여 가져오는 방법이 새로 생성하는 방법보다 컴퓨터 자원을 훨씬 덜 사용.