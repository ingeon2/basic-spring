package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
    //위 컨테이너 참조매서드 잘보기

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류 발생함")
    void findBeanByDuplicate() {
        //MemberRepository bean = ac.getBean(MemberRepository.class);
        //위처럼 뽑아내면 MemberRepository 빈이 SameBeanConfig엔 두개가 있으니 Spring이 뭘 뽑아낼지 모른다.

        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
        //ac.getBean(MemberRepository.class) 를 실행했을 때 NoUniqueBeanDefinitionException 이 예외가 떠야 한다 라는 로직
    }

    @Test
    @DisplayName("같은 타입이 위처럼 여러개일때 예외 안터지려면, 빈 이름을 지정하면 되지롱!")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("그럼 특정 타입을 한꺼번에 조회하고싶으면 어떡하지....?")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

        //여기는 그냥 출력
        for(String key : beansOfType.keySet()){
            System.out.println("key = " + key + "value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2); //MemberService 인터페이스로 만든 빈은 두개니까!
    }

    
    

    @Configuration
    static class SameBeanConfig { //타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류 발생함 을 보여주기 위해 만든 클래스

        @Bean
        public MemberRepository memberRepository1 () {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2 () {
            return new MemoryMemberRepository();
        }
    }
}
