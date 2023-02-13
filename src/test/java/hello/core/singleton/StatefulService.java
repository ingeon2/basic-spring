package hello.core.singleton;

public class StatefulService { //상태를 유지할 경우 발생하는 문제점 예시

    private int price; //상태유지 필드

    public void order(String name, int price){
        System.out.println("name = " + name + "price = " + price);
        this.price = price; //여기에서 문제점 발생하도록 만듦!
        //상태유지 안하려면 5행 private price 지우고, 9행 this.price 지우고,
        //return price 여기에 바로 해야함! (매서드 반환변수 int로 바꿔주고)
        //(입력된 price가 그때그때 다르기 때문에 무상태성 만족.)
    }

    public int getPrice() {
        return price;
    }
}

//싱글톤 방식의 주의점
//싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는
//싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를
//유지(stateful)하게 설계하면 안된다.