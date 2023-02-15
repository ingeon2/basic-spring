package hello.core.scan.filter;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented //여기 세개 어노테이션은 컴포넌트 스캔할 때 스캔당하게 해줄 @Componenent 문서에서 가져온것.
public @interface MyExcludeComponent {
}
