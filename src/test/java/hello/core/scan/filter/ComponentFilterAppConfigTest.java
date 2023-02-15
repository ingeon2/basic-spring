package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentFilterAppConfigTest {
    @Test
    void filterScan() { //BeanA, BeanB는 컴포넌트 에너테이션 둘렀으니 @ComponentScan 할때 스캔된다.
        // 그리고 여기 매서드는 그냥 빈 호출하는 매서드임! @Componont 와 헷갈리지 않기
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull(); //얘는 MyIncludeComponent 이 클래스의 애너테이션 적용했으니.

        assertThrows(
                NoSuchBeanDefinitionException.class, //여기 에러가 떠야한다는 검증.
                () -> ac.getBean("beanB", BeanB.class) //여기 로직 적용하면 바로위의
        );


    }


    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
            //아래 한줄만 설명하면 MyExcludeComponent 클래스에서 만든 에너테이션 적용한놈들은 excludeFilters 해라.
    )
    static class ComponentFilterAppConfig{} //위에서 스프링으로 사용할 클래스
}
