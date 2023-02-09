package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        //MemberService 클래스의 memberService이름으로 꺼내서 
        //memberService 객체에 할당
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        //할당 후 MemberServiceImpl의 클래스인지 여부 확인
        //(AppConfig 클래스 보면 MemberService 객체는 MemberServiceImpl의 클래스어야함.)
    }

    @Test
    @DisplayName("이름 없이 타입으로 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회 (MemberService 인터페이스 아닌 MemberServiceImpl 클래스로)")
    void findBeanByName2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        //But 구체적으로 적는건 별로 안좋음 (역할, 즉 인터페이스인 MemberService 의존이 양호.)
    }

    @Test
    @DisplayName("이름 없는 빈 조회하면..?")
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberService.class));
        //ac.getBean("xxxxx", MemberService.class) 를 실행했을 때 NoSuchBeanDefinitionException 이 예외가 떠야 한다 라는 로직
    }
}
