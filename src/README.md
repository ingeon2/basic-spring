가장 처음 할 일은 예제 만들기임.
discount, member, order 패키지만들어서 인터페이스와 클래스까지 만들고 비즈니스 로직도 만들어서 자바 안에서만 잘 작동되는지 확인한것,  
여기서는 서로가 필요한 객체들을 생성자 주입이 아니라  
전부 다 각각 MemberService memberService = new MemberServiceImpl(); 이런식으로 사용함  
이 내용은 SOLID중에 D, 즉 추상화에 의존해야지, 구체화에 의존하면 안된다 의 원칙을 벗어남  
(memberService는 인터페이스(추상화)고 MemberServiceImpl는 클래스(구체화)니까.)
여기까지가 스프링 핵심 원리 이해1 - 예제 만들기 의 내용임. 오직 순수 자바를 사용.  
//  
//  
//  
그다음 할일은 예제의 기획이 변경되었을때 대처하는 방식을 보며, 문제점을 찾으며 하나하나 좋은 코드(DI,Ioc, 등)로 고쳐나가는것.  
먼저 package hello.core.order; 의 OrderServiceLmpl 클래스에서 할인정책 선택 방식이  
private final DiscountPolicy discountPolicy = new FixDiscountPolicy();  
private final DiscountPolicy discountPolicy = new RateDiscountPolicy();  
에서 무엇을 사용할지에 따라 주석처리해주어야함.  
즉, 위의 코드는  
★OCP, DIP 같은 객체지향 설계 원칙을 위배한것.  
(위에서 OCP는 지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다! 따라서 OCP를 위반.)  
// 어려우면 pdf 다어이그램 볼것  
//  
그러면 위의 문제를 어떻게 해결할까나..?  
위의 문제를 종합하면,  
관심사의 분리  
애플리케이션을 하나의 공연이라 생각해보자. 각각의 인터페이스를 배역(배우 역할)이라 생각하자. 그런데!  
실제 배역 맞는 배우를 선택하는 것은 누가 하는가?  
로미오와 줄리엣 공연을 하면 로미오 역할을 누가 할지 줄리엣 역할을 누가 할지는 배우들이 정하는게  
아니다.   
이전 코드는 마치 로미오 역할(인터페이스)을 하는 레오나르도 디카프리오(구현체, 배우)가 줄리엣  
역할(인터페이스)을 하는 여자 주인공(구현체, 배우)을 직접 초빙하는 것과 같다.   
디카프리오는 공연도 해야하고 동시에 여자 주인공도 공연에 직접 초빙해야 하는 다양한 책임을 가지고 있다.  
//  
관심사를 분리하자  
배우는 본인의 역할인 배역을 수행하는 것에만 집중해야 한다.   
//  
디카프리오는 어떤 여자 주인공이 선택되더라도 똑같이 공연을 할 수 있어야 한다.  
공연을 구성하고, 담당 배우를 섭외하고, 역할에 맞는 배우를 지정하는 책임을 담당하는 별도의 공연 기획자가 나올시점이다.  
공연 기획자를 만들고, 배우와 공연 기획자의 책임을 확실히 분리하자.  
★그러한 공연 기획자가 AppConfig 이다.  
(애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스)  
//  
정리  
AppConfig를 통해서 관심사를 확실하게 분리했다.  
배역, 배우를 생각해보자.  
AppConfig는 공연 기획자다.  
AppConfig는 구체 클래스를 선택한다. 배역에 맞는 담당 배우를 선택한다. 애플리케이션이 어떻게  
동작해야 할지 전체 구성을 책임진다.  
이제 각 배우들은 담당 기능을 실행하는 책임만 지면 된다.  
OrderServiceImpl 은 기능을 실행하는 책임만 지면 된다.  
//  
이후엔 AppConfig를 리팩터링했다.(한눈에 차지하는 영역을 볼 수 있게) 주석을 AppConfig에서 보기  
//  
AppConfig의 등장으로 애플리케이션이 크게 사용 영역과(AppConfig를 뺀 나머지), 객체를 생성하고 구성(Configuration)하는 영역으로 분리  
즉 이젠 AppConfig를 통해 OCP와 DIP 둘다 만족하게 됨  
//  
//  
새로운 할인 정책 개발,  관심사의 분리 AppConfig 등장,  AppConfig 리팩터링 위에서부터 이렇게 세개의 순서로 코딩함.  
//  
좋은 객체 지향 설계의 5가지 원칙의 적용 중에 여기까지 3가지 SRP, DIP, OCP 적용  
SRP - 한 클래스는 하나의 책임만 가져야 한다.  
(구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당 클라이언트 객체는 실행하는 책임만 담당)  
DIP - 프로그래머는 “추상화에 의존해야지, 구체화에 의존하면 안된다.” 의존성 주입은 이 원칙을 따르는 방법 중 하나  
(AppConfig 생성)  
OCP - 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다  
(소프트웨어 요소를 새롭게 확장해도 config만 변경하고, 사용 영역의 변경은 닫혀 있다.)  
//  
IoC,제어의 역전 (Inversion of Control)  
AppConfig가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다. 프로그램의  
제어 흐름은 이제 AppConfig가 가져간다. 예를 들어서 OrderServiceImpl 은 필요한 인터페이스들을  
호출하지만 어떤 구현 객체들이 실행될지 모른다.  
이렇듯 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라  
한다.  
//  
정적인 클래스 의존관계  
import 코드만 보고 의존관계를 쉽게 판단할 수 있다. 정적인 의존관계는 애플리케이션을 실행하지 않아도 분석가능   
(인터페이스와 하위클래스)  
//  
동적인 객체 인스턴스 의존 관계  
애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계다  
(Fix를 사용할지 Rate를 사용할지)  
//  
의존관계 주입을 사용하면 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를  
쉽게 변경할 수 있다.  
//  
IoC 컨테이너, DI 컨테이너  
AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을  
IoC 컨테이너 또는 DI 컨테이너라 한다. (나중엔 Spring 이 할 역할)  
//  
//  
3번의 변경 후  
이젠 Spring을 사용! (only java → Spring)  
by @Configuration,@Bean in AppConfig  
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);  in MemberApp  
AppConfig 환경설정 정보가지고 @Bean 붙은것들 컨테이너에서 생성해서 관리해줌.  
MemberService memberService = applicationContext.getBean("memberService", MemberService.class);  in MemberApp  
"memberService" 이거는 이 이름의 매서드를 찾을거라는 뜻. 그러면 주석 없이  OrderApp 바꿔보기.  
//  
쉽게말해서 applicationContext 에 AppConfig 할당.  
memberService 에 applicationContext를 사용한 매서드(getBean)로 MemberService 할당.  
//  
스프링 컨테이너  
ApplicationContext 를 스프링 컨테이너라 한다.  
기존에는 개발자가 AppConfig 를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제부터는 스프링 컨테이너를 통해서 사용한다.  
스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다.   
여기서 @Bean 이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.   
이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.  
스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.   
( memberService , orderService ) 매서드 in AppConfig  
이전에는 개발자가 필요한 객체를 AppConfig 를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다.   
스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.  
기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고,   
스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.  
//  
//
  
여기서부터는 컨테이너와 빈의 내용 (AppConfig까지 마친 후)  
//  
//  
ApplicationContextInfoTest 클래스 추가로 컨테이너의 Bean 확인.  
빈 이름은 항상 다른 이름을 부여,  
스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입  
예를 들어, (AppConfig 클래스에서 memberService와 orderService는 memberRepository를 의존)  
단순히 자바 코드를 호출하는 것 같지만, 차이가 있다. 이 차이는 뒤에 싱글톤 컨테이너에서 설명.  

Test 패키지 안의 beanfind 패키지 안에서 컨테이너로 올려놓은 빈을 찾는 각각의 방법을 가진 클래스들을 만들었다.
Test에서도 마찬가지로, 구체화에 의존하는것은 좋지 않은 방식이다.  
(DiscountPolicy rateDiscountPolicy O)  
(RateDiscountPolicy rateDiscountPolicy X)  

BeanFactory와 ApplicationContext  
ApplicationContext는 BeanFactory의 기능을 상속받는다.  
ApplicationContext는 빈 관리기능 + 편리한 부가 기능을 제공한다.  
BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용한다.  
BeanFactory나 ApplicationContext를 스프링 컨테이너라 한다.  



ApplicationContext가 스프링 컨테이너이고,  
지금까지는 AppConfig에서 의존관계 설정 후 스프링 컨테이너를 AnnotationConfig 통해 사용했다.(AppConfig.class)      
하지만 XML을 통해 컨테이너를 사용하는 방식도 가능하기에,  또한 추가했다.(XmlAppContext.class, appConfig.xml)  
xml 기반의 appConfig.xml 스프링 설정 정보와 자바 코드로 된 AppConfig.java 설정 정보를 비교해보면 거의 비슷    
  
  
지금까지 정리
기본 로직 만들고(순수 자바)  
AppConfig를 만들어 의존관계 설정했다.(리팩터링 포함, 순수 자바)    
이후 AnnotationConfig를 통해 스프링과 연결했다.  
이후 빈을 어떻게 호출하는지에 대해 beanfind 패키지에서 알아보았다.  
이후 스프링 컨테이너를 사용하는 여러가지 방식(AnnotationConfig, XmlAppContext)을 알아보았다  


스프링은 어떻게 이런 다양한 설정 형식(xml, annotation)을 지원하는 것일까?    
그 중심에는 BeanDefinition 이라는 추상화가 있다.(pdf 17페이지 그림)  
스프링 컨테이너는 자바 코드인지, XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다.  
새로운 형식의 설정 정보가 추가되면, XxxBeanDefinitionReader를 만들어서 BeanDefinition 을 생성하면 된다.  
(Xxx는 Annotated일 수도 있고 Xml일 수도 있다.)  
  
즉, BeanDefinition에 대해서는 너무 깊이있게 이해하기 보다는, 스프링이 다양한 형태의 설정 정보를  
BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하면 된다.  
  
  
여기까지 BeanDefinition, 이후로는 싱글톤.  
싱글톤  
우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다.  
고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.  
해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다.  
★Spring 컨테이너는 그래서 객체를 하나만 생성해주고, 자바에서 순수하게 싱글톤을 사용할 때 생기는 문제점(ex 구체클래스 의존)들 또한 해결해준다!
  
싱글톤 방식의 주의점은..?(무상태성을 유지해야한다!)  
statefulService, statefulServiceTest 클래스에 문제점이 일어날 수 있는 상황을 만들어놓았다.  
  
끝나지 않은 싱글톤의 의문점 in AppConfig  
AppConfig 클래스를 보면, memberService와 orderService 매서드를 실행하면 둘 다 new MemoryMemberRepository() 를 부른다.  
어... 그럼 싱글톤이 깨지는거 아닌가..? 서로 다른 두 매서드에서 두개를 생성하는 셈이니..?  
그냥 직접 봐보자!! 메모리 주소값이 같은지 (in ConfigurationSingletonTest)  
Appconfig의 호출해주는 매서드의 println으로 singleton을 유지하는 것을 보았다 (ConfigurationSingletonTest에서 매서드 실행)  
(예를 들어 memberRepository는 3번 호출되어야 한다. 하지만 1번만 호출되어 전부 같은 객체로 다른 매서드에서 사용된다.)  
위처럼 되는 이유. 즉 완벽한 스프링이 singleton을 실행할 수 있는 이유는 ConfigurationSingletonTest 에서 configurationDeep 매서드를 통해 구현해놓았다!  
(@Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면
생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.)  
이 덕분에 싱글톤 보장..!  

@Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.  
memberRepository() 처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않는다.  
크게 고민할 것이 없다. 스프링 설정 정보는 항상 @Configuration 을 사용하자.  
  
여기까지  
코드 구현, config 클래스 생성, 스프링과 연결(애너테이션 in Appconfig), bean찾기(test), beandef보기(test), 싱글톤 확인(test)하기 까지 왔다.  
  
  
  
AppConfig 클래스에서는 등록하고싶은 빈마다 @Bean 애너테이션을 붙였는데 귀찮지 않은가..?  
그래서 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다.(AutoAppConfig클래스 보기!)  
컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.(에너테이션은 상속x, 스프링에서 자체적으로.)  
@Component : 컴포넌트 스캔에서 사용  
@Controlller : 스프링 MVC 컨트롤러에서 사용  
@Service : 스프링 비즈니스 로직에서 사용  
@Repository : 스프링 데이터 접근 계층에서 사용  
@Configuration : 스프링 설정 정보에서 사용  
  
또 의존관계도 자동으로 주입하는 @Autowired 라는 기능도 제공한다.  
이게 뭔 어려운소리지? 싶을 수 있는데, 차근차근 살펴보자.  
이전에 AppConfig에서는 @Bean 으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다.  
이제는 이런 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 한다.  
@Autowired 는 의존관계를 자동으로 주입해준다  
  
자세하게 MemberServiceImpl 클래스에서 말하자면,  
@Autowired 가 붙은 생성자를 보고,  
스프링에서 MemberServiceImpl 클래스를 빈으로 등록 할 때 생성자에 필요한 클래스인 MemberRepository 를 주입해준다.  
  
MemberRepository 인스턴스를 구체화한 클래스중 어떤 클래스를 주입해줄까..?   

생성자에서 여러 의존관계도 한번에 주입받을 수 있다.(뭔소리지 싶으면 OrderServiceImpl 클래스의 생성자를 보도록 하자.)  
또한, getBean 매서드 사용법도 달라진다. (매서드 명은 오직 AppConfig에만 존재하니까.)  
어려우면 컴포넌트 스캔 pdf 5페이지.  
  
스캔 탐색위치는?  
지정할 수 있음(AutoAppConfig 에서 주석 보기)  
지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스(AutoAppConfig)의 패키지가 시작 위치 (여기선 hello.core)  
권장은 프로젝트 최상단에 AutoAppConfig 놔둬서 거기서부터 찾게 하기.  


필터  
includeFilters : 컴포넌트 스캔 대상을 추가로 지정한다.  
excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정한다.  (Test 패키지의 scan 패키지의 filter 패키지에 구현.)  
Test 패키지의 scan 패키지의 filter 패키지 꼭 한번 읽어보시길.  
includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록된다.  
excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지 않는다.  
  
  

중복되는 이름의 빈 등록  
자동 vs 자동(예를 들어 오더서비스임플과 멤버서비스 임플에 둘다 @Component("Service") 이런식으로 똑같이 이름 붙이는것 )은 에러뜨고,  
자동 vs 수동  
(하나는 이름 그대로이고 수동은 자동으로 스캔되어 가져올 @Bean을 Config클래스에서  다시 똑같은 이름으로 재현하는것)  
은 에러 뜨지 않는다. (오버라이딩 개념, 수동 우선권)  
무엇보다 헷갈리면 pdf 컴포넌트 스캔 마지막 보자.  
  
스프링은 이렇게 등록시켜주지만,  
물론 개발자가 의도적으로 이런 결과를 기대했다면, 자동 보다는 수동이 우선권을 가지는 것이 좋다.  
하지만 현실은 개발자가 의도적으로 설정해서 이런 결과가 만들어지기 보다는 여러 설정들이 꼬여서 이런 결과가 만들어지는 경우가 대부분이다!  
그러면 정말 잡기 어려운 버그가 만들어진다. 항상 잡기 어려운 버그는 애매한 버그다.  
그래서 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다.  
즉, 스프링과 다르게 스프링 부트는 이름이 같은 자동 vs 수동빈 등록을 에러로 뽑아내버림.  