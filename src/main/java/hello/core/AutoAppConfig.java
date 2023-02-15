package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan/*@Component 붙은 클래스들 전부 자동으로 빈으로 등록해줌*/(
        //basePackages = "hello.core", 어디서부터 탐색할지를 지정해주는 내용.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes =  Configuration.class)
) //이전에 AppConfig 사용할때 @Configuration붙여놓은 친구들은 제외하고. (AppConfig 클래스에 붙어있음, 충돌방지하려고.)
public class AutoAppConfig {
}
//기존의 AppConfig와는 다르게 @Bean으로 등록한 클래스가 하나도 없다!
