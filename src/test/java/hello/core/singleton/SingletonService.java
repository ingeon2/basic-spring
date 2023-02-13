package hello.core.singleton;

public class SingletonService {//singleton 패키지의 SingletonTest 클래스에선 싱글톤이 아닌 예시를 보여주었고,
                               //여기서는 순수 자바로 singleton 만드는법 보여주기.

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    //여기까지 오직 하나의 객체 instance 만 사용하도록 만든것.
    //바로 아래는, 생성자를 private로 만들어줘서 다른데서 new 생성자로 만들지도 못하도록 못박아버림.

    private SingletonService(){}
    
    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
//1. static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.
//2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
//3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서 new
//키워드로 객체 인스턴스가 생성되는 것을 막는다.

//싱글톤 패턴을 구현하는 방법은 여러가지가 있다. 여기서는 객체를 미리 생성해두는 가장 단순하고 안전한 방법을 선택했다.