package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //TreadA : A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //TreadB : B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //TreadA : A사용자 주문 금액 조회
        //(원래대로라면 10000원 나와야함, 여기서는 A 주문하고 A 가격조회 사이에 B사용자 주문이 끼어든 상태라서 20000이 나와버림.)
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        //진짜 공유필드는 조심해야 한다! 스프링 빈은 항상 무상태(stateless)로 설계하자.
        //무상태 설계는 StatefulService 클래스에 주석으로 달아놓음. 물론 여기 클래스에서도 바꿔야할 부분 있음!
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}